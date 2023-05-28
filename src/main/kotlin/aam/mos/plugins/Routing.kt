package aam.mos.plugins

import aam.mos.controller.core.Controller
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Application.configureRouting(di: DI) {
    install(Resources)
    install(AutoHeadResponse)

    val controllers: Set<Controller> by di.instance()

    routing {
        get("health") {
            call.respond(HttpStatusCode.OK, 1)
        }
        controllers.forEach { controller ->
            application.log.info("Registering '$controller' routes...")
            controller.apply { routes() }
        }
    }
}