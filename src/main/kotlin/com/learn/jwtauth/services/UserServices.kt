package com.learn.jwtauth.services

import com.learn.jwtauth.model.CreateUserRequest
import com.learn.jwtauth.model.UserResponse
import org.springframework.stereotype.Component


interface UserServices {

    fun create(createUserRequest: CreateUserRequest) : UserResponse


}