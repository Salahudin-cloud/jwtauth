package com.learn.jwtauth.controller

import com.learn.jwtauth.model.CreateUserRequest
import com.learn.jwtauth.model.UserResponse
import com.learn.jwtauth.model.WebResponse
import com.learn.jwtauth.services.UserServices
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1")
class UserController (val userServices: UserServices) {
    @PostMapping(
        value = ["/user"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createUser(@RequestBody body: CreateUserRequest) : WebResponse<UserResponse> {
        val userResponse = userServices.create(body)
        return WebResponse(
            code = 200,
            status = "Ok",
            data = userResponse
        )
    }
}