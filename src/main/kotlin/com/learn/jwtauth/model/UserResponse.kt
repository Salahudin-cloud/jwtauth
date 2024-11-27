package com.learn.jwtauth.model

import java.util.*

data class UserResponse(
    val uuid: String,
    var email : String,
    var username: String,
    var password: String,
    var role:String,
    val currentPage: Int? = null,
    val itemsPerPage : Int? = null,
    val createdAt : Date,
    var updateAt: Date?
)
