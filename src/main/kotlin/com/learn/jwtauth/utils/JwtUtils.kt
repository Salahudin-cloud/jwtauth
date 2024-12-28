package com.learn.jwtauth.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils {
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(username: String, role: String): String {
        val prefixedRole = if (role.startsWith("ROLE_")) role else "ROLE_${role.uppercase(Locale.getDefault())}"
        val claims = mapOf("role" to prefixedRole) // Add the role to the claims
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
            .signWith(secretKey)
            .compact()
    }

    fun getSecretKey() : SecretKey = secretKey



    // Extract username from token
    fun extractUsername(token: String): String {
        return parseClaimsJws(token).body.subject
    }

    // Validate token
    fun validateToken(token: String, username: String): Boolean {
        val extractedUsername = extractUsername(token)
        return (username == extractedUsername && !isTokenExpired(token))
    }

    // Check if the token is expired
    private fun isTokenExpired(token: String): Boolean {
        val expiration = parseClaimsJws(token).body.expiration
        return expiration.before(Date())
    }

    // Helper method to parse claims from the token (JWS format)
    private fun parseClaimsJws(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
    }
}