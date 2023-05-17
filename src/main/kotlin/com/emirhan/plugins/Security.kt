package com.emirhan.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.respond

fun Application.configureSecurity() {

    authentication {
        jwt {
            realm = TokenManager.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(TokenManager.secret))
                    .withAudience(TokenManager.audience)
                    .withIssuer(TokenManager.issuer)
                    .build()
            )
            validate { credential ->
                if (!credential.payload.getClaim("username").asString().isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.response.headers.append(HttpHeaders.WWWAuthenticate, "Bearer")
                call.respond(status = HttpStatusCode.Unauthorized, "Unauthorized!")
            }
        }
    }
}
