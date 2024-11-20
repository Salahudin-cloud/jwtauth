package com.learn.jwtauth.controller

import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.CostumeUserDetailsServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class UserSecurityConfig {

    @Bean
    fun customUserDetailsServices(userRepository: UserRepository): CostumeUserDetailsServices {
        return CostumeUserDetailsServices(userRepository)
    }

    // development only allow all request
    @Bean
    open fun defaultFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{it.disable()}
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }

        return http.build()
    }

//    @Bean
//    @Order(1)
//    open fun adminSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        val adminPath = arrayOf("/api/v1/user")
//
//        http
//            .securityMatcher(*adminPath)
//            .authorizeHttpRequests{ it.anyRequest().hasRole("admin") }
//            .httpBasic {  }
//        return http.build()
//    }

//    @Bean
//    @Order(2)
//    open fun userSecurityFilterChain(http: HttpSecurity) : SecurityFilterChain {
//        val adminPath = arrayOf("/api/v1/user")
//
//        http
//            .securityMatcher(*adminPath)
//            .authorizeHttpRequests{ it.requestMatchers(HttpMethod.GET, "/api/v1/user").hasRole("user") }
//            .httpBasic {  }
//
//        return http.build()
//    }


}
