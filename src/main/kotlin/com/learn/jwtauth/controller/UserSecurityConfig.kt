package com.learn.jwtauth.controller

import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.CostumeUserDetailsServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class UserSecurityConfig {


    fun customUserDetailsServices(userRepository: UserRepository): CostumeUserDetailsServices {
        return CostumeUserDetailsServices(userRepository)
    }


    // need to authenticate
    @Bean
    open fun defaultFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{it.disable()}
            .authorizeHttpRequests {
                it.anyRequest().authenticated()
            }

            .httpBasic {  }

        return http.build()
    }

    // development only allow all request
//    @Bean
//    open fun defaultFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .csrf{it.disable()}
//            .authorizeHttpRequests {
//                it.anyRequest().permitAll()
//            }
//
//        return http.build()
//    }

//    @Bean
//    @Order(1)
//    open fun adminSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
//
//        http
//            .csrf { it.disable() }
//            .securityMatcher("/api/v1/user")
//            .authorizeHttpRequests{ it.anyRequest().hasRole("ADMIN") }
//            .httpBasic {  }
//        return http.build()
//    }
//
//    @Bean
//    @Order(2)
//    open fun userSecurityFilterChain(http: HttpSecurity) : SecurityFilterChain {
//        http
//            .csrf { it.disable() }
//            .securityMatcher("/api/v1/user")
//            .authorizeHttpRequests{ it.requestMatchers(HttpMethod.GET, "/api/v1/user").hasRole("USER") }
//            .httpBasic {  }
//
//        return http.build()
//    }


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
