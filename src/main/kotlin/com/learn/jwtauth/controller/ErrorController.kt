package com.learn.jwtauth.controller

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


}

