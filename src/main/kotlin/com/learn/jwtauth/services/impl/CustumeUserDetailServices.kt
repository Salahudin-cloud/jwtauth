package com.learn.jwtauth.services.impl

import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustumeUserDetailServices(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = username?.let { userRepository.findByUsername(it) } ?: throw UsernameNotFoundException("Cant find user: $username")

        return CustomUserDetails(user)
    }

}