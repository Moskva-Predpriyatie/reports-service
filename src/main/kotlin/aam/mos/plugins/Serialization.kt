package aam.mos.plugins

import aam.mos.util.appSerializersModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI

fun Application.configureSerialization(di: DI) {
    val clientJson = Json {
        ignoreUnknownKeys = true
        encodeDefaults = false
        isLenient = true
        serializersModule = appSerializersModule
    }
    install(ContentNegotiation) {
        json(clientJson)
    }
}
