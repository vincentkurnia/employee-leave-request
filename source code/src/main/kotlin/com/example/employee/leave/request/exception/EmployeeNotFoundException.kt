package com.example.employee.leave.request.exception

import org.springframework.http.HttpStatus

class EmployeeNotFoundException: CustomException("Employee not found") {
    override val statusCode = HttpStatus.NOT_FOUND
}