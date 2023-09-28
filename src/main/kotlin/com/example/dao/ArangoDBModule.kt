package com.example.dao
import com.arangodb.ArangoDB
import com.arangodb.Protocol
import com.arangodb.entity.LoadBalancingStrategy
import com.example.config.DatabaseConfig

class ArangoDBModule {
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
}