package aam.mos.di

import aam.mos.repository.*
import aam.mos.util.appSerializersModule
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.kodein.di.*
import java.security.SecureRandom
import kotlin.random.Random
import kotlin.random.asKotlinRandom

val appModule = DI.Module(name = "app") {
    bind<Random>() with instance(SecureRandom().asKotlinRandom())

    bind<JWTVerifier>() with eagerSingleton {
        val secret = instance<SecretRepository>().getSecret()
        JWT.require(Algorithm.HMAC256(secret)).build()
    }

    bind<HttpClient>() with eagerSingleton {
        val clientJson = Json {
            ignoreUnknownKeys = true
            encodeDefaults = false
            isLenient = true
            serializersModule = appSerializersModule
        }
        HttpClient(Java) {
            install(ContentNegotiation) {
                json(clientJson)
            }
        }
    }

    bind<SecretRepository>() with eagerSingleton { new(::SecretRepositoryImpl) }
    bind<CodeRepository>() with eagerSingleton { new(::CodeRepositoryImpl) }
    bind<TimeRepository>() with eagerSingleton { new(::TimeRepositoryImpl) }
    bind<OtpRepository>() with eagerSingleton { new(::OtpRepositoryImpl) }
    bind<RefreshRepository>() with eagerSingleton { new(::RefreshRepositoryImpl) }
    bind<UserRepository>() with eagerSingleton { new(::UserRepositoryImpl) }
    bind<EmailRepository>() with eagerSingleton { new(::EmailRepositoryImpl) }
    bind<StaticRepository>() with eagerSingleton { new(::StaticRepositoryImpl) }
    bind<NeuralRepository>() with eagerSingleton { new(::NeuralRepositoryImpl) }
    bind<ReportRepository>() with eagerSingleton { new(::ReportRepositoryImpl) }
}
