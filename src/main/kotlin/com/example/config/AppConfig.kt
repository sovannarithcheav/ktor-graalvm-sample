package com.example.config

import io.ktor.server.config.ApplicationConfig

data class AppConfig(
    var host: String = "",
    var port: Int = 0,
) {
    init {
        val config = ApplicationConfig("application.yaml")
        port = config.property("ktor.deployment.port").getString().toInt()
        host = config.property("ktor.deployment.host").getString()
    }
}