package aam.mos.controller

import aam.mos.controller.core.Controller
import aam.mos.service.StaticService
import aam.mos.util.unitRoute
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

class StaticController(
    private val staticService: StaticService
) : Controller {

    override fun Route.routes() = unitRoute(STATIC_ROUTE) {

        get<Industries> {
            val industries = staticService.getIndustries()
            call.respond(HttpStatusCode.OK, industries)
        }
    }

    @Serializable
    @Resource("/industries")
    class Industries
}

private const val STATIC_ROUTE = "static"