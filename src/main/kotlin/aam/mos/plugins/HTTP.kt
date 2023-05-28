package aam.mos.plugins

import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.application.*
import org.kodein.di.DI

fun Application.configureHTTP(di: DI) {
/*    routing {
        swaggerUI(path = "openapi")
    }
    routing {
        openAPI(path = "openapi")
    }*/
    install(XForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        anyHost()
        allowOrigins { true }
        allowHeaders { true }
        allowNonSimpleContentTypes = true
    }
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024)
        }
    }
}
