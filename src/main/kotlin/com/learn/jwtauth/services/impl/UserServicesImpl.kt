package com.learn.jwtauth.services.impl

import com.learn.jwtauth.entity.User
import com.learn.jwtauth.model.CreateUserRequest
import com.learn.jwtauth.model.UserResponse
import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.UserServices
import com.learn.jwtauth.validation.ValidationUtils
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Date
import java.util.UUID
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Service
class UserServicesImpl(
    val userRepository: UserRepository,
    val validationUtils: ValidationUtils
) : UserServices{
    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtils.validate(createUserRequest)
        val user = User(
            uuid = UUID.randomUUID().toString(),
            email = createUserRequest.email!!,
            username = createUserRequest.username!!,
            password = BCryptPasswordEncoder().encode(createUserRequest.password!!),
            role = createUserRequest.role!!,
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