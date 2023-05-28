package aam.mos.repository

import aam.mos.model.domain.RefreshSession
import aam.mos.util.withJedis

import io.ktor.server.config.*
import redis.clients.jedis.JedisPool

interface RefreshRepository {

    suspend fun save(id: String): RefreshSession

    suspend fun remove(id: String)

    suspend fun rotate(id: String, refreshId: String): RefreshSession?
}

class RefreshRepositoryImpl(
    private val pool: JedisPool,
    private val codeRepository: CodeRepository,
    config: ApplicationConfig
) : RefreshRepository {

    private val expire = config.property("jwt.refreshExpire").getString().toLong() - 30 // Чтобы не отдавать токен, который просрочится через 30 секунд

    override suspend fun save(id: String): RefreshSession {
        val refreshId = codeRepository.createCode(8)
        pool.withJedis { jedis ->
            jedis.setex(createKey(id), expire, refreshId)
        }
        return RefreshSession(refreshId, id)
    }

    override suspend fun remove(id: String) {
        pool.withJedis { jedis ->
            jedis.del(createKey(id))
        }
    }

    override suspend fun rotate(id: String, refreshId: String): RefreshSession? {
        val key = createKey(id)
        val refreshSession = pool.withJedis { jedis ->
            jedis.getDel(key)?.let {
                if (refreshId != it) return@let null

                val newRefreshId = codeRepository.createCode(8)
                jedis.setex(key, expire, newRefreshId)
                RefreshSession(newRefreshId, id)
            }
        }
        return refreshSession
    }

    private fun createKey(id: String) = "user:$id/refresh"
}
