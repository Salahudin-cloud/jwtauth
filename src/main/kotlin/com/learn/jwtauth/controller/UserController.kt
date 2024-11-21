package com.learn.jwtauth.controller

import com.learn.jwtauth.entity.User
import com.learn.jwtauth.model.CreateUserRequest
import com.learn.jwtauth.model.UpdateUserRequest
import com.learn.jwtauth.model.UserResponse
import com.learn.jwtauth.model.WebResponse
import com.learn.jwtauth.services.UserServices
import org.apache.juli.logging.Log
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.hibernate.query.sqm.tree.SqmNode.log

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

    @GetMapping(
        value = ["/user/{idUser}"],
        produces = ["application/json"]
    )
    fun getUser(@PathVariable("idUser") id : String  ) : WebResponse<UserResponse> {
        val userResponse = userServices.get(id)

        return WebResponse(
            code = 200,
            status = "Ok",
            data = userResponse
        )
    }

    @PutMapping(
        value = ["/user/{idUser}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateUser(@PathVariable("idUser") id : String,
                   @RequestBody body : UpdateUserRequest) : WebResponse<UserResponse> {
        val userResponse = userServices.update(id, body)
        return WebResponse(
            code = 200,
            status = "Ok",
            data = userResponse
        )
    }

    @DeleteMapping(
        value = ["/user/{idUser}"],
        produces = ["application/json"],
    )
    fun deleteUser(@PathVariable("idUser") id:String ) : WebResponse<String> {
        log.info("id user deleted : $id")
        userServices.delete(id)
        return WebResponse(
            code = 200,
            status = "Ok",
            data = "Data deleted successfully"
        )
    }
}