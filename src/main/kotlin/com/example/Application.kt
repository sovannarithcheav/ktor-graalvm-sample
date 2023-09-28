package com.example

import com.example.config.AppConfig
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.RequestValidation
import kh.org.soramitsu.util.AppContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    val applicationConfig = AppConfig()
    val appModule = module {
        singleOf(::AppContext)
    }
    embeddedServer(CIO, port = applicationConfig.port, host = applicationConfig.host) {
        configureRouting()
        install(Koin) {
            slf4jLogger()
            modules(appModule)
        }
        install(RequestValidation)
        install(ContentNegotiation) {
            register(ContentType.Application.Json, JacksonConverter())
        }
    }.start(wait = true)
}

