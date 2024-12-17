package com.learn.jwtauth.controller

import com.learn.jwtauth.exception.AuthenticatedException
import com.learn.jwtauth.exception.NotFoundException
import com.learn.jwtauth.model.WebResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(ConstraintViolationException::class)
    fun validationExeceptionHandler(err : ConstraintViolationException) : WebResponse<String>{
        return WebResponse(
            code = 400,
            status = "Bad request",
            data = err.message!!
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundExeptionHandler(err : NotFoundException) : WebResponse<String> {
        return WebResponse(
            code = 404,
            status = "Not found",
            data = "Cannot find the user with the uuid"
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun failedToLogin(err: AuthenticatedException) : WebResponse<String> {
        return WebResponse(
            code = 404,
            status = "Not Found",
            data = "Wrong username or password"
        )
    }

}

