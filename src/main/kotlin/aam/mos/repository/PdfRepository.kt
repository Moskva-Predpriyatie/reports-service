package aam.mos.repository

import aam.mos.model.dto.ReportResultDto
import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import io.ktor.server.config.*
import java.io.ByteArrayInputStream
import java.io.File
import java.io.OutputStream
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface PdfService {

    fun renderPdf(name: String, report: ReportResultDto, outputStream: OutputStream)
}

class PdfServiceImpl(
    config: ApplicationConfig
) : PdfService {

    private val path = config.property("pdf").getString()

    override fun renderPdf(name: String, report: ReportResultDto, outputStream: OutputStream) {

        val properties = ConverterProperties().setBaseUri(path)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val data = mapOf(
            "date" to LocalDate.now().format(formatter),
            "user" to name,
            "district" to report.request.district,
            "rentalCost" to report.rentalCost!!.toReportString(),
            "landCost" to report.landCost!!.toReportString(),
            "facilityCost" to report.facilityCost!!.toReportString(),
            "equipmentCost" to report.equipmentCost!!.toReportString(),
            "salaryCost" to report.salaryCost!!.toReportString(),
            "insuranceCost" to report.insuranceCost!!.toReportString(),
            "from" to report.from.toReportString(),
            "to" to report.to.toReportString()
        )

        val regex = "\\{\\{.+}}".toRegex()
        val text = File(path, "paper.html")
            .readText()
            .replace(regex) { result ->
                val key = result.value.substring(2, result.value.length - 2)
                data[key]!!
            }

        HtmlConverter.convertToPdf(
            ByteArrayInputStream(text.toByteArray()),
            outputStream,
            properties
        )
    }

    private fun BigDecimal.toReportString(): String {
        return setScale(2, RoundingMode.HALF_UP).toPlainString()
    }
}
