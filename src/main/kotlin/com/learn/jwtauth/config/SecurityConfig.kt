package com.learn.jwtauth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig (
    private  val jwtAuthenticationFilter : JwtAuthenticationFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManage(authenticationConfiguration: AuthenticationConfiguration) : AuthenticationManager = authenticationConfiguration.authenticationManager

    @Bean
    fun secuityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http
            .csrf{it.disable()}
            .authorizeHttpRequests{
                it.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated()
            }
            .sessionManagement{it.disable()}

        return http.build()
    }


}