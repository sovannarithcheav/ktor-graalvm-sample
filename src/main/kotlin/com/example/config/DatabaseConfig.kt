package com.example.config

import com.arangodb.ArangoDB
import com.arangodb.Protocol
import com.arangodb.entity.LoadBalancingStrategy
import io.ktor.server.config.*

data class DatabaseConfig(
    var host: String = "",
    var port: Int = 0,
    var username: String = "",
    var password: String = "",
    var database: String = "",
    var maxConnection: Int = 10,
) {
    init {
        val config = ApplicationConfig("application.yaml")
        port = config.property("ktor.data-source.port").getString().toInt()
        host = config.property("ktor.data-source.host").getString()
        username = config.property("ktor.data-source.user").getString()
        password = config.property("ktor.data-source.password").getString()
        database = config.property("ktor.data-source.database").getString()
        maxConnection = config.property("ktor.data-source.max-connection").getString().toInt()
    }
}

fun createDatabaseConnection(): ArangoDB {
    val databaseConfig = DatabaseConfig()
    return ArangoDB.Builder()
        .host(databaseConfig.host, databaseConfig.port)
        .user(databaseConfig.username)
        .password(databaseConfig.password)
        .maxConnections(databaseConfig.maxConnection) // pooling
        .protocol(Protocol.HTTP_JSON)
        .loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN)
        .build()
}