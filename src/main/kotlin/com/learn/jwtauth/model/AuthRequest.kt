package com.learn.jwtauth.model

import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)
