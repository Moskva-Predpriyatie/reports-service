package aam.mos.di.util

import io.ktor.server.config.*
import org.kodein.di.DirectDIAware
import org.kodein.di.instance

fun DirectDIAware.configString(path: String): String =
    instance<ApplicationConfig>().property(path).getString()
