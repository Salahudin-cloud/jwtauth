package com.learn.jwtauth.model

data class AuthResponse(
    var username: String,
    var token: String,
)