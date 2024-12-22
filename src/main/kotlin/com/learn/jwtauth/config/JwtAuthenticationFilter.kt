package com.learn.jwtauth.config

import com.learn.jwtauth.services.CustomUserDetails
import com.learn.jwtauth.services.UserServices
import com.learn.jwtauth.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (
    private val jwtUtils: JwtUtils,
    private val customUserDetailsService: UserDetailsService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (!authHeader.isNullOrEmpty() && authHeader.startsWith("Bearer ")) {
            // extract token without "Bearer"
            val token = authHeader.substring(7)
            val username = jwtUtils.extractUsername(token)
            if (jwtUtils.validateToken(
                   token = token, username = username
                )) {
                val userDetails = customUserDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                SecurityContextHolder.getContext().authentication = authentication

            }else {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.writer.write("Invalid Token")
                return

            }


        }

        filterChain.doFilter(request, response)
    }

}