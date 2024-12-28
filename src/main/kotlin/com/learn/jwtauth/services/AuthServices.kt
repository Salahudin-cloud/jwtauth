package com.learn.jwtauth.services

import com.learn.jwtauth.model.AuthRequest
import com.learn.jwtauth.model.AuthResponse

interface AuthServices {
    fun authenticatedUser(authRequest: AuthRequest) : AuthResponse
}