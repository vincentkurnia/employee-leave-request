package com.example.employee.leave.request.handler

import com.example.employee.leave.request.service.EmployeeService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class EmployeeHandler(
    private val service: EmployeeService
) {
    suspend fun getEmployee(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toInt()
        val result = service.getEmployee(id)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }
}