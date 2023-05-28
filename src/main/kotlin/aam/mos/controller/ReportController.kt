package aam.mos.controller

import aam.mos.controller.core.Controller
import aam.mos.plugins.Auth
import aam.mos.plugins.JwtClaim
import aam.mos.repository.PdfService
import aam.mos.service.NeuralService
import aam.mos.service.ReportService
import aam.mos.service.UserService
import aam.mos.util.unitRoute
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class ReportController(
    private val neuralService: NeuralService,
    private val reportService: ReportService,
    private val userService: UserService,
    private val pdfService: PdfService
) : Controller {

    override fun Route.routes() = unitRoute(REPORTS_ROUTE) {

        post<Neural> {
            val report = call.receive<ReportPostBody>()
            call.respond(HttpStatusCode.OK, neuralService.getNeuralNetwork(report))
        }

        post<Gradient> {
            val report = call.receive<ReportPostBody>()
            call.respond(HttpStatusCode.OK, neuralService.getGradientBoosting(report))
        }

        authenticate(Auth.JWT, optional = true) {
            post<Reports> {
                val id = JwtClaim.getIdFromCallOrNull(call)
                val reportInput = call.receive<ReportPostBody>()

                val report = reportService.createReport(id, reportInput)
                call.respond(HttpStatusCode.OK, report)
            }
        }

        authenticate(Auth.JWT) {
            get<Reports> {
                val id = JwtClaim.getidFromCall(call)

                val reports = reportService.getReports(id)
                call.respond(HttpStatusCode.OK, reports)
            }

            get<ReportPdf> {
                val id = JwtClaim.getidFromCall(call)
                val user = userService.getUser(id)
                val report = reportService.getReport(id, it.id)

                call.respondOutputStream(
                    contentType = ContentType.Application.Pdf,
                    status = HttpStatusCode.OK
                ) {
                    pdfService.renderPdf(user.name ?: "пользователь", report, this)
                }
            }
        }
    }

    @Serializable
    class ReportPostBody(
        val personnel: Int,
        val district: String,
        @SerialName("branch") val orgType: Int,
        val isIndividual: Boolean,
        val landSquare: Int,
        val facilitySquare: Int,
        val equipment: Int,
        val isLandRental: Boolean,
        val isFacilityRental: Boolean,
        val rentalSquare: Int? = null
    )

    @Serializable
    @Resource("/")
    class Reports

    @Serializable
    @Resource("/{id}/file")
    class ReportPdf(
        val id: String
    )

    @Serializable
    @Resource("/neural")
    class Neural

    @Serializable
    @Resource("/gradient")
    class Gradient
}

private const val REPORTS_ROUTE = "reports"
