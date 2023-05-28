package aam.mos.controller

import aam.mos.controller.core.Controller
import aam.mos.exception.UnauthorizedException
import aam.mos.model.dto.TokenDto
import aam.mos.plugins.Auth
import aam.mos.plugins.JwtClaim
import aam.mos.service.AuthService
import aam.mos.util.*
import com.auth0.jwt.JWTVerifier
import io.konform.validation.Validation
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

class AuthController(
    private val authService: AuthService,
    private val jwtVerifier: JWTVerifier
) : Controller {

    override fun Route.routes() = unitRoute(AUTH_ROUTE) {

        get<OtpCode> {
            OtpCode.validate(it).throwIfInvalid()

            authService.issueCode(it.email, call.request.origin.remoteHost)
            call.respond(HttpStatusCode.OK, "OK")
        }

        get<Token> {
            Token.validate(it).throwIfInvalid()

            val ip = call.request.origin.remoteHost
            val tokenPair = authService.login(it.email, it.code, ip)
            call.response.cookies.appendRefreshToken(tokenPair.refresh)
            call.respond(HttpStatusCode.OK, tokenPair)
        }

        post<Refresh> {
            val refreshToken = call.request.cookies[Auth.COOKIE_REFRESH]
            val decodedJWT = runCatching { jwtVerifier.verify(refreshToken) }.getOrElse { throw UnauthorizedException() }

            val tokenPair = authService.refresh(id = decodedJWT.getClaim(JwtClaim.KEY_id).asString(), refreshId = decodedJWT.id)
            call.response.cookies.appendRefreshToken(tokenPair.refresh)
            call.respond(HttpStatusCode.OK, tokenPair)
        }

        authenticate(Auth.JWT) {
            delete<TokenDelete> {
                val id = JwtClaim.getidFromCall(call)
                authService.logout(id)
                call.response.cookies.clearTokens()
                call.respond(HttpStatusCode.OK, "OK")
            }
        }
    }

    private fun ResponseCookies.appendRefreshToken(refresh: TokenDto) {
        append(Auth.COOKIE_REFRESH, refresh.token, httpOnly = true, secure = true, maxAge = refresh.maxAge, path = "/auth/token/refresh")
    }

    private fun ResponseCookies.clearTokens() {
        append(Auth.COOKIE_REFRESH, "", maxAge = 0L, path = "/auth/token/refresh")
    }

    @Serializable
    @Resource("/otp-code")
    class OtpCode(val email: String) {

        companion object {
            val validate = Validation {
                OtpCode::email { email() }
            }
        }
    }

    @Serializable
    @Resource("/token")
    class Token(val email: String, val code: String) {

        companion object {
            val validate = Validation {
                Token::email { email() }
                Token::code { numeric(); length(6) }
            }
        }
    }

    @Serializable
    @Resource("/token")
    class TokenDelete

    @Serializable
    @Resource("/token/refresh")
    class Refresh
}

private const val AUTH_ROUTE = "auth"
