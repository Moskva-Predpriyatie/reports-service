package aam.mos

import aam.mos.di.*
import aam.mos.model.dbo.IndustryDbo
import aam.mos.plugins.*
import aam.mos.repository.EmailRepository
import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.util.reflect.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val application = this

    val di = DI {
        bind<Application>() with instance(application)
        bind<ApplicationEnvironment>() with instance(environment)
        bind<ApplicationConfig>() with instance(environment.config)
        importAll(appModule, redisModule, mongoModule, controllerModule, serviceModule)
    }

    configureSerialization(di)
    configureMonitoring(di)
    configureHTTP(di)
    configureStatusPages(di)
    configureSecurity(di)
    configureRouting(di)
}
