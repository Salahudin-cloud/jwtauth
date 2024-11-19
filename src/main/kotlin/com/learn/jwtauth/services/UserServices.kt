package com.learn.jwtauth.services

import com.learn.jwtauth.model.CreateUserResponse
import com.learn.jwtauth.model.UserResponse

interface UserServices {

    fun create(createUserResponse: CreateUserResponse) : UserResponse


}