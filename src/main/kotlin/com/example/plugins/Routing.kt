package com.example.plugins

import com.example.dao.ArangoDBModule
import com.example.dao.User
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.oracle.svm.core.annotate.Inject
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import io.ktor.serialization.jackson.*
import org.koin.java.KoinJavaComponent.inject
import org.springframework.context.ApplicationContext

fun Application.configureRouting() {
    val arangoDB = ArangoDBModule().createDatabaseConnection()
    val appContext by inject<ApplicationContext>(ApplicationContext::class.java)
    routing {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
            jackson {
                configure(SerializationFeature.INDENT_OUTPUT, true)
                setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                    indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                    indentObjectsWith(DefaultIndenter("  ", "\n"))
                })
            }
        }
            get("/") {
            val users = arangoDB.db("HRM").query("FOR u IN USERS RETURN u", Any::class.java).asListRemaining()
            call.respond(users)
        }
    }
}
