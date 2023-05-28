package aam.mos.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*
import com.auth0.jwt.JWTVerifier
import io.ktor.http.auth.*
import org.kodein.di.DI
import org.kodein.di.instance

object Auth {

    const val JWT = "auth-jwt"
    const val COOKIE_REFRESH = "refresh"
    const val COOKIE_ACCESS = "access"
}

object JwtClaim {

    const val KEY_id = "id"

    fun getidFromCall(call: ApplicationCall): String {
        return requireNotNull(getIdFromCallOrNull(call))
    }

    fun getIdFromCallOrNull(call: ApplicationCall): String? {
        val principal = call.principal<JWTPrincipal>() ?: return null
        return principal.payload.getClaim(KEY_id).asString()
    }
}

fun Application.configureSecurity(di: DI) {

    val jwtVerifier: JWTVerifier by di.instance()

    authentication {
        jwt(Auth.JWT) {
            verifier(jwtVerifier)
            validate { credential ->
                if (credential.payload.claims.containsKey(JwtClaim.KEY_id)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
