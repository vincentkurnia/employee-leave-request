package com.example.employee.leave.request.handler

import com.example.employee.leave.request.configuration.RouterConfiguration
import com.example.employee.leave.request.model.Employee
import com.example.employee.leave.request.service.EmployeeService
import com.example.employee.leave.request.service.LeaveRequestService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

class EmployeeHandlerTest {
    private val employeeService = mockk<EmployeeService>()
    private val leaveRequestService = mockk<LeaveRequestService>()
    private val webTestClient = WebTestClient.bindToRouterFunction(
        RouterConfiguration().mainRoute(
            EmployeeHandler(employeeService),
            LeaveRequestHandler(leaveRequestService)
        )
    ).build()

    @Test
    fun getEmployeeTest() {
        val id = 1
        val employee = Employee(1, "John", "Doe", "john.doe@company.com", null)

        coEvery { employeeService.getEmployee(id) } returns employee

        webTestClient.get()
            .uri {
                it.path("/api/employee/{id}")
                it.build(id)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody(Employee::class.java).isEqualTo(employee)

        coVerify(exactly = 1) { employeeService.getEmployee(id) }
    }
}