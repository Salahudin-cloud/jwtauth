package com.learn.jwtauth.model


data class CreateUserResponse(
    val uuid : String,
    var email : String,
    var username: String,
    var password: String,
    var role:String,
)
