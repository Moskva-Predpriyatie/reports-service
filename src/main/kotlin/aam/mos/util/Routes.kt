package aam.mos.util

import io.ktor.server.routing.*

fun Route.unitRoute(path: String, build: Route.() -> Unit) {
    route(path, build)
}