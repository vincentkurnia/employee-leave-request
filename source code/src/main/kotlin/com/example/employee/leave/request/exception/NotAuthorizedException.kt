package com.example.employee.leave.request.exception

import org.springframework.http.HttpStatus

class NotAuthorizedException: CustomException("Not Authorized") {
    override val statusCode = HttpStatus.UNAUTHORIZED
}