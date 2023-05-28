package aam.mos.repository

import io.ktor.server.config.*

interface SecretRepository {

    fun getSecret(): ByteArray
}

class SecretRepositoryImpl(
    config: ApplicationConfig
) : SecretRepository {

    private val secret = config.property("jwt.secret").getString().toByteArray()

    override fun getSecret(): ByteArray = secret
}
