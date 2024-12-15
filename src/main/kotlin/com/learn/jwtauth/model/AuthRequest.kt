package com.learn.jwtauth.model

import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @field:NotBlank
    var username: String,
    @field:NotBlank
    var password: String,
)
