package com.emirhan.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.emirhan.User
import java.util.Date

object TokenManager {
    fun generateToken(user: User): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withExpiresAt(getExpirationTime())
            .sign(Algorithm.HMAC256(secret))
    }

    const val issuer = "issuer"
    const val audience = "audience"
    const val secret = "secret"
    const val realm = "realm"

    private fun getExpirationTime() = Date(System.currentTimeMillis() + 20000)
}