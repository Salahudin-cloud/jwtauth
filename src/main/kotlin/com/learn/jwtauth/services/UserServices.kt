package com.learn.jwtauth.services

import com.learn.jwtauth.model.CreateUserRequest
import com.learn.jwtauth.model.ListUserRequest
import com.learn.jwtauth.model.UpdateUserRequest
import com.learn.jwtauth.model.UserResponse
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


interface UserServices {

    fun create(createUserRequest: CreateUserRequest) : UserResponse

    fun get(uuid : String ) : UserResponse

    fun update(uuid: String, updateUserRequest: UpdateUserRequest) : UserResponse

    fun delete(uuid: String)

    fun list(listUserRequest: ListUserRequest) : List<UserResponse>

}