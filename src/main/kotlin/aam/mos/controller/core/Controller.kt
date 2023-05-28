package aam.mos.controller.core

import io.ktor.server.routing.*

interface Controller {

    fun Route.routes()
}