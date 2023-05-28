package aam.mos.repository

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.server.config.*

interface EmailRepository {

    suspend fun sendCode(email: String, code: String): Boolean
}

class EmailRepositoryImpl(
    private val httpClient: HttpClient,
    config: ApplicationConfig
) : EmailRepository {

    private val url = config.property("email.mailgun.apiUrl").getString()
    private val apiKey = config.property("email.mailgun.apiKey").getString()
    private val from = config.property("email.mailgun.from").getString()

    override suspend fun sendCode(email: String, code: String): Boolean {
        val response = httpClient.post(url) {
            basicAuth("api", apiKey)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("from", from)
                        append("to", email)
                        append("subject", "Подтверждение Email")
                        append("text", "Ваш код подтверждения: $code")
                    }
                )
            )
        }

        return response.status == HttpStatusCode.OK
    }
}
