package com.example.employee.leave.request.service

import com.example.employee.leave.request.dto.LeaveRequestDto
import com.example.employee.leave.request.exception.EmployeeNotFoundException
import com.example.employee.leave.request.model.LeaveRequest
import com.example.employee.leave.request.repository.LeaveRequestRepository
import io.micrometer.observation.ObservationRegistry
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class LeaveRequestServiceTest {
    private val repository = mockk<LeaveRequestRepository>()
    private val employeeService = mockk<EmployeeService>()
    private val observationRegistry = mockk<ObservationRegistry>(relaxed = true)
    private lateinit var service: LeaveRequestService

    @BeforeTest
    fun setup() {
        service = LeaveRequestService(repository, employeeService, observationRegistry)
    }

    @Test
    fun getLeaveTypesTest() = runTest {
        val leaveTypes = listOf(
            LeaveRequest.Type(1, "Sick"),
            LeaveRequest.Type(2, "Annual")
        )

        coEvery { repository.getLeaveTypes() } returns leaveTypes

        val result = service.getLeaveTypes()

        coVerify(exactly = 1) { repository.getLeaveTypes() }

        assertEquals(leaveTypes, result)
    }

    @Test
    fun getLeaveRequestStatusesTest() = runTest {
        val statuses = listOf(
            LeaveRequest.Status(1, "Approved"),
            LeaveRequest.Status(2, "Rejected")
        )

        coEvery { repository.getLeaveRequestStatuses() } returns statuses

        val result = service.getLeaveRequestStatuses()

        coVerify(exactly = 1) { repository.getLeaveRequestStatuses() }

        assertEquals(statuses, result)
    }

    @Test
    fun getQuotasTest() = runTest {
        val id = 100

        coEvery { repository.getQuotas(id) } returns null

        assertFailsWith<EmployeeNotFoundException> { service.getQuotas(id) }

        coVerify(exactly = 1) { repository.getQuotas(id) }
    }

    @Test
    fun submitRequestTest() = runTest {
        val request = LeaveRequestDto(1, LocalDate.parse("2025-06-10"), LocalDate.parse("2025-06-20"), "Annual", 1)

        coEvery { repository.getQuotas(request.employeeId) } returns 20
        coEvery { repository.updateQuotas(request.employeeId, 10) } returns 1L
        coEvery { repository.submitLeaveRequest(any<LeaveRequest>()) } returns 1L

        service.submitRequest(request)

        coVerify(exactly = 1) { repository.getQuotas(request.employeeId) }
        coVerify(exactly = 1) { repository.updateQuotas(request.employeeId, 10) }
        coVerify(exactly = 1) { repository.submitLeaveRequest(any<LeaveRequest>()) }
    }
}