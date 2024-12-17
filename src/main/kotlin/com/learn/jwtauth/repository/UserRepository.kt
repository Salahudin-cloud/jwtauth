package com.learn.jwtauth.repository

import com.learn.jwtauth.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun findByUsername(username: String) : User?
}