package com.learn.jwtauth.validation

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator

class ValidationUtils(val validator: Validator) {
    fun validate(any: Any) {
        val result = validator.validate(any)

        if (result.isEmpty()) {
            throw ConstraintViolationException(result)
        }
    }
}