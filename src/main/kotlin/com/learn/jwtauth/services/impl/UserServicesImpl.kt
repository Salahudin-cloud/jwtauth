package com.learn.jwtauth.services.impl

import com.learn.jwtauth.entity.User
import com.learn.jwtauth.exception.NotFoundException
import com.learn.jwtauth.model.CreateUserRequest
import com.learn.jwtauth.model.ListUserRequest
import com.learn.jwtauth.model.UpdateUserRequest
import com.learn.jwtauth.model.UserResponse
import com.learn.jwtauth.repository.UserRepository
import com.learn.jwtauth.services.UserServices
import com.learn.jwtauth.validation.ValidationUtils
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServicesImpl(
    val userRepository: UserRepository,
    val validationUtils: ValidationUtils,
    val passwordEncoder: PasswordEncoder
) : UserServices{
    @PreAuthorize("hasRole('ADMIN')")
    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtils.validate(createUserRequest)
        val user = User(
            uuid = UUID.randomUUID().toString(),
            email = createUserRequest.email!!,
            username = createUserRequest.username!!,
            password = passwordEncoder.encode(createUserRequest.password!!),
            role = createUserRequest.role!!,
            createdAt = Date(),
            updateAt = null
        )
        userRepository.save(user)
        return userResponse(user)
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    override fun get(uuid: String): UserResponse {
        val getUser = findUser(uuid)
        return userResponse(getUser)
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun update(uuid: String, updateUserRequest: UpdateUserRequest): UserResponse {
        validationUtils.validate(updateUserRequest)
        val user = findUser(uuid)

        user.apply {
            email = updateUserRequest.email!!
            username = updateUserRequest.username!!
            password = passwordEncoder.encode(updateUserRequest.password!!)
            role = updateUserRequest.role!!
            updateAt = Date()
        }
        userRepository.save(user)
        return userResponse(user)
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun delete(uuid: String) {

        val getUser = findUser(uuid)
        userRepository.delete(getUser)
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun list(listUserRequest: ListUserRequest): List<UserResponse> {
        val page = userRepository.findAll(PageRequest.of(listUserRequest.currentPage, listUserRequest.itemsPerPage))

        return page.content.map {
            user -> UserResponse(
                uuid = user.uuid,
                email = user.email,
                username = user.username,
                password = user.password,
                role = user.role,
                currentPage = listUserRequest.currentPage,
                itemsPerPage = listUserRequest.itemsPerPage,
                createdAt = user.createdAt,
                updateAt = user.updateAt
            )
        }

    }


    private fun findUser(uuid: String) : User {

        val user = userRepository.findByIdOrNull(uuid)
        if (user == null) {
            throw NotFoundException()
        }else {
            return user
        }

    }


    private fun userResponse(user: User, currentPage: Int? = null, itemPerPage:Int? = null) : UserResponse {
        return UserResponse(
            uuid = user.uuid,
            email = user.email,
            username = user.username,
            password = user.password,
            role = user.role,
            currentPage = currentPage,
            itemsPerPage = itemPerPage,
            createdAt = user.createdAt,
            updateAt = user.updateAt
        )
    }
}