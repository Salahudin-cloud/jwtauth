package com.learn.jwtauth.model

import jakarta.validation.constraints.NotBlank


data class CreateUserRequest(
    @field:NotBlank
    var email : String?,
    @field:NotBlank
    var username: String?,
    @field:NotBlank
    var password: String?,
    @field:NotBlank
    var role:String?,
)
