package com.example.employee.leave.request.service

import com.example.employee.leave.request.exception.EmployeeNotFoundException
import com.example.employee.leave.request.model.Employee
import com.example.employee.leave.request.repository.EmployeeRepository
import com.example.employee.leave.request.utils.observeSuspend
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val repository: EmployeeRepository,
    private val observationRegistry: ObservationRegistry
) {
    suspend fun getEmployee(id: Int): Employee {
        val observation = Observation.createNotStarted("retrieve_employee_data", observationRegistry)

        return observation.observeSuspend {
            repository.getEmployee(id) ?: throw EmployeeNotFoundException()
        }
    }
}