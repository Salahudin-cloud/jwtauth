package com.learn.jwtauth.services

import com.learn.jwtauth.repository.UserRepository
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CostumeUserDetailsServices(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {

        log.info("Loading user by username: $username")

        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        log.info("User found: ${user.username}")


        return User.builder()
            .username(user.username)
            .password(user.password)
            .roles(user.role.uppercase())
            .build()
    }
}
