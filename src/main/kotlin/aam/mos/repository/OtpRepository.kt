package aam.mos.repository

import aam.mos.exception.ForbiddenException
import aam.mos.util.multi
import aam.mos.util.withJedis
import io.ktor.server.config.*
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

interface OtpRepository {

    fun issueOtp(email: String, ip: String): String

    fun checkOtp(email: String, ip: String, otp: String): Boolean
}

class OtpRepositoryImpl(
    private val pool: JedisPool,
    private val codeRepository: CodeRepository,
    config: ApplicationConfig
) : OtpRepository {

    private val expire = config.property("otp.expire").getString().toLong()
    private val limit = config.property("otp.limit").getString().toInt()
    private val limitTime = config.property("otp.limitTime").getString().toLong()
    private val emailLimit = config.property("email.limit").getString().toInt()
    private val emailLimitTime = config.property("email.limitTime").getString().toLong()
    private val ipLimit = config.property("email.ip.limit").getString().toInt()
    private val ipLimitTime = config.property("email.ip.limitTime").getString().toLong()

    override fun issueOtp(email: String, ip: String): String =
        pool.withJedis { jedis ->
            val ipCountKey = createIpCountKey(ip)
            checkIpLimit(jedis, ipCountKey)

            val emailCountKey = createEmailCountKey(email)
            checkEmailLimit(jedis, emailCountKey)

            val otpCountKey = createOtpCountKey(email)
            checkOtpLimit(jedis, otpCountKey)

            val otp = codeRepository.createDigitCode(6)

            jedis.multi {
                incr(emailCountKey)
                expire(emailCountKey, emailLimitTime)
                incr(ipCountKey)
                expire(ipCountKey, ipLimitTime)
            }
            jedis.setex(createKey(email), expire, otp)
            return@withJedis otp
        }

    override fun checkOtp(email: String, ip: String, otp: String): Boolean =
        pool.withJedis { jedis ->
            val ipCountKey = createIpCountKey(ip)
            checkIpLimit(jedis, ipCountKey)

            val otpCountKey = createOtpCountKey(email)
            checkOtpLimit(jedis, otpCountKey)

            val key = createKey(email)
            val isValid = jedis.get(key) == otp
            if (isValid) {
                val emailCountKey = createEmailCountKey(email)
                jedis.del(key, otpCountKey, emailCountKey)
            } else {
                jedis.multi {
                    incr(otpCountKey)
                    expire(otpCountKey, limitTime)
                }
            }
            return@withJedis isValid
        }

    private fun checkOtpLimit(jedis: Jedis, countKey: String) {
        val count = jedis.get(countKey)
        if (count != null && count.toInt() > limit) throw ForbiddenException()
    }

    private fun checkEmailLimit(jedis: Jedis, countKey: String) {
        val count = jedis.get(countKey)
        if (count != null && count.toInt() > emailLimit) throw ForbiddenException()
    }

    private fun checkIpLimit(jedis: Jedis, countKey: String) {
        val count = jedis.get(countKey)
        if (count != null && count.toInt() > ipLimit) throw ForbiddenException()
    }

    private fun createKey(email: String) = "email:$email/otp"

    private fun createOtpCountKey(email: String) = "email:$email/otp/count"

    private fun createEmailCountKey(email: String) = "email:$email/count"

    private fun createIpCountKey(ip: String) = "ip:$ip/count"
}
