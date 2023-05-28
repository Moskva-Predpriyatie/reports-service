package aam.mos.controller

import aam.mos.controller.core.Controller
import aam.mos.plugins.Auth
import aam.mos.plugins.JwtClaim
import aam.mos.service.UserService
import aam.mos.util.unitRoute
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.patch
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import kotlinx.coroutines.delay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class UserController(
    private val userService: UserService
) : Controller {

    override fun Route.routes() = unitRoute(USERS_ROUTE) {
        authenticate(Auth.JWT) {
            get<Me> {
                val id = JwtClaim.getidFromCall(call)
                val userDto = userService.getUser(id)
                call.respond(HttpStatusCode.OK, userDto)
            }

            patch<Me> {
                val body = call.receive<MePutBody>()

                val id = JwtClaim.getidFromCall(call)
                val userDto = userService.updateUser(
                    id = id,
                    updates = buildMap {
                        if (body.name != null) put("name", body.name)
                        if (body.surname != null) put("surname", body.surname)
                        if (body.patronymic != null) put("patronymic", body.patronymic)
                        if (body.orgName != null) put("orgName", body.orgName)
                        if (body.taxId != null) put("taxId", body.taxId)
                        if (body.orgUrl != null) put("orgUrl", body.orgUrl)
                        if (body.orgType != null) put("orgType", body.orgType)
                        if (body.city != null) put("city", body.city)
                        if (body.position != null) put("position", body.position)
                    }
                )
                call.respond(HttpStatusCode.OK, userDto)
            }
        }
    }
}

@Serializable
@Resource("/me")
class Me

@Serializable
class MePutBody(
    @SerialName("email") val email: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("surname") val surname: String? = null,
    @SerialName("patronymic") val patronymic: String? = null,
    @SerialName("orgName") val orgName: String? = null,
    @SerialName("taxId") val taxId: String? = null,
    @SerialName("orgUrl") val orgUrl: String? = null,
    @SerialName("orgType") val orgType: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("position") val position: String? = null
)

private const val USERS_ROUTE = "users"
