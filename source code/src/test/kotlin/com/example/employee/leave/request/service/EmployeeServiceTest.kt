package com.example.employee.leave.request.service

import com.example.employee.leave.request.model.Employee
import com.example.employee.leave.request.repository.EmployeeRepository
import io.micrometer.observation.ObservationRegistry
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class EmployeeServiceTest {
    private val repository = mockk<EmployeeRepository>()
    private val observationRegistry = mockk<ObservationRegistry>(relaxed = true)
    private lateinit var service: EmployeeService

    @BeforeTest
    fun setup() {
        service = EmployeeService(repository, observationRegistry)
    }

    @Test
    fun getEmployeeTest() = runTest {
        val id = 1
        val employeeData = Employee(1, "John", "Doe", "john.doe@company.com", null)

        coEvery { repository.getEmployee(id) } returns employeeData

        val result = service.getEmployee(1)

        coVerify(exactly = 1) { repository.getEmployee(1) }

        assertEquals(result, employeeData)
    }
}