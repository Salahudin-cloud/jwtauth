package com.learn.jwtauth.controller

import com.learn.jwtauth.model.AuthRequest
import com.learn.jwtauth.model.AuthResponse
import com.learn.jwtauth.services.AuthServices
import com.learn.jwtauth.utils.JwtUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
   private val authServices: AuthServices,
   private val jwtUtils: JwtUtils,
   private val authenticationManager: AuthenticationManager
) {
    @PostMapping(
        value = ["/login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody body: AuthRequest): AuthResponse {
        return authServices.authenticatedUser(body)
    }

}