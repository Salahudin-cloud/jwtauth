package com.learn.jwtauth.controller

import com.learn.jwtauth.model.*
import com.learn.jwtauth.services.UserServices
import org.springframework.web.bind.annotation.*

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
        userServices.delete(id)
        return WebResponse(
            code = 200,
            status = "Ok",
            data = "Data deleted successfully"
        )
    }

    @GetMapping(
        value = ["/user"],
        produces = ["application/json"]
    )
    fun listUser(
        @RequestParam(value = "currentPage" , defaultValue = "0") currentPage: Int,
        @RequestParam(value = "itemPerPage" , defaultValue = "10") itemPerPage: Int
    ): WebResponse<List<UserResponse>> {
        val requestResponse = ListUserRequest(currentPage, itemPerPage)
        val res = userServices.list(requestResponse)

        return WebResponse(
            code = 200,
            status = "Ok",
            data = res
        )
    }
}