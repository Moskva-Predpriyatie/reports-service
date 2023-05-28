package aam.mos.plugins

import com.auth0.jwt.exceptions.JWTDecodeException
import aam.mos.exception.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.SerializationException
import org.kodein.di.DI

fun Application.configureStatusPages(di: DI) {
    install(StatusPages) {
        exception<UnauthorizedException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.Unauthorized, e.message.orEmpty())
        }
        exception<ValidationException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.BadRequest, e.message.orEmpty())
        }
        exception<SerializationException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.BadRequest, e.message.orEmpty())
        }
        exception<BadRequestException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.BadRequest, e.message.orEmpty())
        }
        exception<JWTDecodeException> { call: ApplicationCall, _ ->
            call.respond(HttpStatusCode.BadRequest, "Bad token")
        }
        exception<UnavailableException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.ServiceUnavailable, e.message.orEmpty())
        }
        exception<ForbiddenException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.Forbidden, e.message.orEmpty())
        }
        exception<NotFoundException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.NotFound, e.message.orEmpty())
        }
        exception<ContentTooLargeException> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.PayloadTooLarge, e.message.orEmpty())
        }
        exception<Throwable> { call: ApplicationCall, e ->
            call.respond(HttpStatusCode.BadRequest, e.message.orEmpty())
        }
    }
}
