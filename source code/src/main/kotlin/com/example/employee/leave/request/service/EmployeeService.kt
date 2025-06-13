package com.example.employee.leave.request.service

import com.example.employee.leave.request.exception.EmployeeNotFoundException
import com.example.employee.leave.request.model.Employee
import com.example.employee.leave.request.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val repository: EmployeeRepository
) {
    suspend fun getEmployee(id: Int): Employee {
        return repository.getEmployee(id) ?: throw EmployeeNotFoundException()
    }
}