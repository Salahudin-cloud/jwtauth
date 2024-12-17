package com.learn.jwtauth.utils

import io.jsonwebtoken.Jwts
import javax.crypto.SecretKey
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtUtils {
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)


    fun generateToken (username : String) : String {
        val claims = HashMap<String, Any>()
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(secretKey)
            .compact()
    }

    private fun extractUsername(token: String): String = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJwt(token).body.subject

    fun validateToken(token : String, username : String) : Boolean {
        val extractedUsername = extractUsername(token)
        return  (username == extractedUsername && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJwt(token).body.expiration
        return expiration.before(Date())
    }
}