package com.learn.jwtauth.services.impl

import com.learn.jwtauth.entity.User
import com.learn.jwtauth.exception.AuthenticatedException
import com.learn.jwtauth.model.AuthRequest
import com.learn.jwtauth.model.AuthResponse
import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.AuthServices
import com.learn.jwtauth.utils.JwtUtils
import com.learn.jwtauth.validation.ValidationUtils
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServicesImpl(
    val userRepository: UserRepository,
    val validationUtils: ValidationUtils,
    val passwordEncoder: PasswordEncoder,
    val jwtUtils: JwtUtils
) : AuthServices{
    override fun authenticateUser(authRequest: AuthRequest): AuthResponse {
        validationUtils.validate(authRequest)

        val userData = userRepository.findByUsername(authRequest.username)
            ?: throw AuthenticatedException()

        // Compare the provided password with the hashed password
        if (!passwordEncoder.matches(authRequest.password, userData.password)) {
            throw AuthenticatedException()
        }

        val token = jwtUtils.generateToken(authRequest.username)

        return AuthResponse(
            username = userData.username,
            token = token
        )
    }



}