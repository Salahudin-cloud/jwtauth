package com.learn.jwtauth.config

import com.learn.jwtauth.services.impl.CustumeUserDetailServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig (
    private  val jwtAuthenticationFilter : JwtAuthenticationFilter,
    private val customeUserDetails: CustumeUserDetailServices
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManage(authenticationConfiguration: AuthenticationConfiguration) : AuthenticationManager = authenticationConfiguration.authenticationManager

    @Bean
    fun daoAuthenticationProvider() : DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        // set custom service
        provider.setUserDetailsService(customeUserDetails)
        // set the password encoder
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    }


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/v1/auth/**").permitAll() // Public endpoints
                it.requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasRole("ADMIN") // Admin protected endpoints
                it.requestMatchers(HttpMethod.PUT, "/api/v1/user/**").hasRole("ADMIN")
                it.requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasRole("ADMIN")
                it.anyRequest().authenticated() // All other requests must be authenticated
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java) // Add filter before username/password filter

        return http.build()
    }


}