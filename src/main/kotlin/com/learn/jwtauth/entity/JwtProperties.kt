package com.learn.jwtauth.entity

data class JwtProperties(
    val key: String,
    val accessTokenExpiration : Long,
    val refreshTokenExpiration: Long
)
