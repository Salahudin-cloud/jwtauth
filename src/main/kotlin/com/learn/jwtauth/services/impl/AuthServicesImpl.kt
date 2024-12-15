package com.learn.jwtauth.services.impl

import com.learn.jwtauth.model.AuthRequest
import com.learn.jwtauth.model.AuthResponse
import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.AuthServices
import com.learn.jwtauth.validation.ValidationUtils
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServicesImpl(
    val userRepository: UserRepository,
    val validationUtils: ValidationUtils,
    val passwordEncoder: PasswordEncoder
) : AuthServices{
    override fun authenticateUser(authRequest: AuthRequest): AuthResponse {
        validationUtils.validate(authRequest)

        val userData = userRepository.getUserByUsernameAndPassword(authRequest.username, authRequest.password)



    }

}