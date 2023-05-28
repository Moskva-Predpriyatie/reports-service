package aam.mos.service

import aam.mos.exception.UnauthorizedException
import aam.mos.exception.UnavailableException
import aam.mos.model.dto.LoginTokenPairDto
import aam.mos.model.dto.TokenDto
import aam.mos.model.dto.TokenPairDto
import aam.mos.plugins.JwtClaim
import aam.mos.repository.EmailRepository
import aam.mos.repository.RefreshRepository
import aam.mos.repository.TimeRepository
import aam.mos.repository.SecretRepository
import aam.mos.repository.OtpRepository
import aam.mos.repository.UserRepository
import aam.mos.util.toBase64
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import java.util.*

interface AuthService {

    suspend fun issueCode(email: String, ip: String)

    suspend fun login(email: String, code: String, ip: String): LoginTokenPairDto

    suspend fun refresh(id: String, refreshId: String): TokenPairDto

    suspend fun logout(id: String)
}

class AuthServiceImpl(
    private val secretRepository: SecretRepository,
    private val otpRepository: OtpRepository,
    private val userRepository: UserRepository,
    private val refreshRepository: RefreshRepository,
    private val emailRepository: EmailRepository,
    private val timeRepository: TimeRepository,
    config: ApplicationConfig
) : AuthService {

    private val isEnabled = config.property("email.mailgun.enabled").getString().toBoolean()
    private val accessExpire = config.property("jwt.accessExpire").getString().toLong()
    private val refreshExpire = config.property("jwt.refreshExpire").getString().toLong()

    override suspend fun issueCode(email: String, ip: String) {
        if (!isEnabled) return
        val code = otpRepository.issueOtp(email, ip)
        if (!emailRepository.sendCode(email, code)) throw UnavailableException()
    }

    override suspend fun login(email: String, code: String, ip: String): LoginTokenPairDto {
        if (isEnabled && !otpRepository.checkOtp(email, ip, code)) throw UnauthorizedException()

        val existingUser = userRepository.getSimpleUserByEmail(email)

        val id = (existingUser?._id ?: userRepository.createUser(email)._id).toBase64()

        val algorithm = Algorithm.HMAC256(secretRepository.getSecret())
        val currentTime = timeRepository.timestampMillis()
        val accessExpireAt = currentTime + accessExpire * 1000
        val refreshExpireAt = currentTime + refreshExpire * 1000

        val refreshId = refreshRepository.save(id)

        val access = JWT.create()
            .withClaim(JwtClaim.KEY_id, id)
            .withExpiresAt(Date(accessExpireAt))
            .sign(algorithm)

        val refresh = JWT.create()
            .withClaim(JwtClaim.KEY_id, id)
            .withJWTId(refreshId.refreshId)
            .withExpiresAt(Date(refreshExpireAt))
            .sign(algorithm)

        return LoginTokenPairDto(
            isRegistered = existingUser == null || !existingUser.isFull,
            access = TokenDto(access, accessExpire),
            refresh = TokenDto(refresh, refreshExpire)
        )
    }

    override suspend fun refresh(id: String, refreshId: String): TokenPairDto {
        val refreshSession = refreshRepository.rotate(id, refreshId) ?: throw UnauthorizedException()

        val algorithm = Algorithm.HMAC256(secretRepository.getSecret())
        val currentTime = timeRepository.timestampMillis()
        val accessExpireAt = currentTime + accessExpire * 1000
        val refreshExpireAt = currentTime + refreshExpire * 1000

        val access = JWT.create()
            .withClaim(JwtClaim.KEY_id, refreshSession.id)
            .withExpiresAt(Date(accessExpireAt))
            .sign(algorithm)

        val refresh = JWT.create()
            .withClaim(JwtClaim.KEY_id, refreshSession.id)
            .withJWTId(refreshSession.refreshId)
            .withExpiresAt(Date(refreshExpireAt))
            .sign(algorithm)

        return TokenPairDto(
            access = TokenDto(access, accessExpire),
            refresh = TokenDto(refresh, refreshExpire)
        )
    }

    override suspend fun logout(id: String) {
        refreshRepository.remove(id)
    }
}
