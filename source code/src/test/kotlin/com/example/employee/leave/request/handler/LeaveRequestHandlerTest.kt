package com.example.employee.leave.request.handler

import com.example.employee.leave.request.configuration.RouterConfiguration
import com.example.employee.leave.request.dto.GetLeaveRequestDto
import com.example.employee.leave.request.dto.LeaveRequestDto
import com.example.employee.leave.request.model.LeaveRequest
import com.example.employee.leave.request.service.EmployeeService
import com.example.employee.leave.request.service.LeaveRequestService
import io.mockk.*
import org.springframework.http.HttpMethod
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate
import kotlin.test.Test

class LeaveRequestHandlerTest {
    private val employeeService = mockk<EmployeeService>()
    private val leaveRequestService = mockk<LeaveRequestService>()
    private val webTestClient = WebTestClient.bindToRouterFunction(
        RouterConfiguration().mainRoute(
            EmployeeHandler(employeeService),
            LeaveRequestHandler(leaveRequestService)
        )
    ).build()

    @Test
    fun getLeaveTypesTest() {
        val leaveTypes = listOf(
            LeaveRequest.Type(1, "Sick"),
            LeaveRequest.Type(2, "Annual")
        )

        coEvery { leaveRequestService.getLeaveTypes() } returns leaveTypes

        webTestClient.get()
            .uri("/api/employee/leave/types")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(LeaveRequest.Type::class.java)
            .contains(*leaveTypes.toTypedArray())
            .hasSize(leaveTypes.size)

        coVerify(exactly = 1) { leaveRequestService.getLeaveTypes() }
    }

    @Test
    fun getLeaveRequestStatusesTest() {
        val statuses = listOf(
            LeaveRequest.Status(1, "Approved"),
            LeaveRequest.Status(2, "Rejected")
        )

        coEvery { leaveRequestService.getLeaveRequestStatuses() } returns statuses

        webTestClient.get()
            .uri("/api/employee/leave/statuses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(LeaveRequest.Status::class.java)
            .contains(*statuses.toTypedArray())
            .hasSize(statuses.size)

        coVerify(exactly = 1) { leaveRequestService.getLeaveRequestStatuses() }
    }

    @Test
    fun submitRequestTest() {
        val request = LeaveRequestDto(1, LocalDate.parse("2025-06-10"), LocalDate.parse("2025-06-20"), "Annual", 1)

        coEvery { leaveRequestService.submitRequest(request) } just runs

        webTestClient.post()
            .uri("/api/employee/leave/request")
            .bodyValue(request)
            .exchange()
            .expectStatus().isNoContent

        coVerify(exactly = 1) { leaveRequestService.submitRequest(request) }
    }

    @Test
    fun getRequestsTest() {
        val request = GetLeaveRequestDto(1, 1, 1)
        val results = listOf(
            LeaveRequest(1, LocalDate.parse("2025-06-10"), LocalDate.parse("2025-06-20"), LocalDate.parse("2025-05-10"), "Annual", 1, 1, 1)
        )

        coEvery { leaveRequestService.getRequests(request) } returns results

        webTestClient.method(HttpMethod.GET)
            .uri("/api/employee/leave/request")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(LeaveRequest::class.java)
            .contains(*results.toTypedArray())

        coVerify(exactly = 1) { leaveRequestService.getRequests(request) }
    }
}