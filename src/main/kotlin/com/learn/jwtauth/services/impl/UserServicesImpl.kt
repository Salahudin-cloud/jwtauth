package com.learn.jwtauth.services.impl

import com.learn.jwtauth.entity.User
import com.learn.jwtauth.model.CreateUserResponse
import com.learn.jwtauth.model.UserResponse
import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.UserServices
import com.learn.jwtauth.validation.ValidationUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Date

class UserServicesImpl(
    val userRepository: UserRepository,
    val validationUtils: ValidationUtils
) : UserServices{
    override fun create(createUserResponse: CreateUserResponse): UserResponse {

        validationUtils.validate(createUserResponse)

        val user = User(
            uuid = createUserResponse.uuid!!,
            email = createUserResponse.email!!,
            username = createUserResponse.username!!,
            password = BCryptPasswordEncoder().encode(createUserResponse.role!!),
            role = createUserResponse.role!!,
            createdAt = Date(),
            updateAt = null
        )

        userRepository.save(user)

        return userResponse(user)

    }


    private fun userResponse(user: User) : UserResponse {
        return UserResponse(
            uuid = user.uuid,
            email = user.email,
            username = user.username,
            password = user.password,
            role = user.role,
            createdAt = user.createdAt,
            updateAt = user.updateAt
        )
    }
}