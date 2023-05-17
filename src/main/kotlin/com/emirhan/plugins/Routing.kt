package com.emirhan.plugins

import com.emirhan.Token
import com.emirhan.User
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        authenticate {
            get("/authHello") {
                call.respondText("Authenticated Hello World!")
            }
        }
        get("/") {
            call.respondText("Hello World!")
        }
        post("/login") {
            val token = TokenManager.generateToken(User("user", "1234"))
            call.respond(Token(token, "this_is_refresh_token", 20000))
        }
        get("/refreshToken") {
            val token = TokenManager.generateToken(User("user", "1234"))
            call.respond(Token(token, "this_is_refresh_token", 20000))
        }
    }
}
