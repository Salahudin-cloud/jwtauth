package com.learn.jwtauth.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "user_account")
data class User(
    @Id
    val uuid: String,
    @Column(name = "email")
    var email : String,
    @Column(name = "username")
    var username: String,
    @Column(name= "password")
    var password: String,
    @Column(name = "role")
    var role:String,
    @Column(name = "created_at")
    val createdAt : Date,
    @Column(name = "update_at")
    var updateAt: Date?

)
