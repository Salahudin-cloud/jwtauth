package com.learn.jwtauth.config

import com.learn.jwtauth.services.impl.CustumeUserDetailServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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
    fun secuityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http
            .csrf{it.disable()}
            .authorizeHttpRequests{
                it.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated()
            }
            .sessionManagement{it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }


}