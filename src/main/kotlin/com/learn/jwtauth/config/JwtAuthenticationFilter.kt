package com.learn.jwtauth.config

import com.learn.jwtauth.utils.JwtUtils
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*


@Component
class JwtAuthenticationFilter (
    private val jwtUtils: JwtUtils,
    private val customUserDetailsService: UserDetailsService
): OncePerRequestFilter() {
    private val logger: org.slf4j.Logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwtToken = authHeader.substring(7)
            val username = jwtUtils.extractUsername(jwtToken)
            val secretKey = jwtUtils.getSecretKey()

            // Extract the role from the JWT token
            val role = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(jwtToken).body["role"] as String

            logger.info("Extracted role from token: $role") // Log extracted role

            // Ensure that role is prefixed with "ROLE_"
            val prefixedRole = if (role.startsWith("ROLE_")) role else "ROLE_${role.uppercase(Locale.getDefault())}"
            val authorities = listOf(SimpleGrantedAuthority(prefixedRole))

            if (SecurityContextHolder.getContext().authentication == null) {
                val authToken = UsernamePasswordAuthenticationToken(username, null, authorities)
                SecurityContextHolder.getContext().authentication = authToken
                val auth = SecurityContextHolder.getContext().authentication
                logger.info("Authentication authorities: ${auth.authorities}") // Log authorities
            }
        }
        filterChain.doFilter(request, response)
    }


}