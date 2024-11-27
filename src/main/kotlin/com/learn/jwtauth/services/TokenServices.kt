package com.learn.jwtauth.services

import com.learn.jwtauth.entity.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date


@Service
class TokenServices(
    jwtProperties: JwtProperties
) {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String = Jwts.builder()
        .claims()
        .subject(userDetails.username)
        .issuedAt(Date(System.currentTimeMillis() + 1000 * 60 *60))
        .expiration(expirationDate)
        .and()
        .signWith(secretKey)
        .compact()

    fun isValid(token: String, userDetails: UserDetails) : Boolean {
        val username = extractUser(token)
        return userDetails.username == username && !isExpired(token)
    }


    fun isExpired(token : String) : Boolean = getAllClaims(token).expiration.before(Date(System.currentTimeMillis() + 1000 * 60 *60))

    fun extractUser(token : String): String? = getAllClaims(token).subject

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()
        return parser.parseSignedClaims(token)
            .payload
    }
}