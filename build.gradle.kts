@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jib)
}

group = "ge.tips"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

if (project.hasProperty("docker.username") && project.hasProperty("docker.password")) {
    jib {
        to {
            image = "octaone/mos"
            tags = setOf("latest")
            auth {
                username = project.property("docker.username").toString()
                password = project.property("docker.password").toString()
            }
        }
        container {
            mainClass = "io.ktor.server.netty.EngineMain"
            jvmFlags = listOf("-server", "-XX:+UseG1GC", "-XX:+UseStringDeduplication")
            args = listOf("-config=/app/application.conf")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.std)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.reactor)
    implementation(libs.reactor.core)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.compression)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.forwardedHeader)
    implementation(libs.ktor.server.defaultHeaders)
    implementation(libs.ktor.server.autoHead)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.ktor.server.callId)
    implementation(libs.ktor.server.callLogging)
    implementation(libs.ktor.server.metrics.micrometer)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.java)
    implementation(libs.ktor.client.contentNegotiation)

    implementation(libs.kodein)

    implementation(libs.kmongo)
    implementation(libs.kmongo.coroutines.serialization)

    implementation(libs.logback.classic)
    implementation(libs.micrometer.registry.prometheus)
    implementation(libs.jedis)
    implementation(libs.konform)
    implementation(libs.itext)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}