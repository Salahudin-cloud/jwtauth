package com.learn.jwtauth.config

import com.learn.jwtauth.services.UserServices
import com.learn.jwtauth.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter (
    private val jwtUtils: JwtUtils,
    private val userServices: UserServices
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        // check if there is a bearer token in the authorization header

        if (!authHeader.isNullOrEmpty() && authHeader.startsWith("Bearer ")) {
            // extract token without "Bearer"
            val token = authHeader.substring(7)
            val username = jwtUtils.extractUsername(token)
            if (jwtUtils.validateToken(
                   token = token, username = username
                )) {
                val userData = userServices.getUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userData,
                    null,
                    userData.au
                )
            }


        }
    }

}