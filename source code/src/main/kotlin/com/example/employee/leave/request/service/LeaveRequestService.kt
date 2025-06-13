package com.example.employee.leave.request.service

import com.example.employee.leave.request.dto.*
import com.example.employee.leave.request.exception.*
import com.example.employee.leave.request.model.LeaveRequest
import com.example.employee.leave.request.repository.LeaveRequestRepository
import com.example.employee.leave.request.utils.getCurrentDate
import org.springframework.stereotype.Service
import java.time.Period

@Service
class LeaveRequestService(
    private val repository: LeaveRequestRepository,
    private val employeeService: EmployeeService
) {
    suspend fun getLeaveTypes(): List<LeaveRequest.Type> {
        return repository.getLeaveTypes()
    }

    suspend fun getLeaveRequestStatuses(): List<LeaveRequest.Status> {
        return repository.getLeaveRequestStatuses()
    }

    suspend fun getQuotas(id: Int): Int {
        return repository.getQuotas(id) ?: throw EmployeeNotFoundException()
    }

    suspend fun getRequest(id: Int): LeaveRequest {
        return repository.getRequest(id) ?: throw LeaveRequestNotFoundException()
    }

    suspend fun getRequests(request: GetLeaveRequestDto): List<LeaveRequest> {
        employeeService.getEmployee(request.employeeId)

        return repository.getRequests(request)
    }

    suspend fun getRequestForApprover(request: GetLeaveRequestForApproverDto): List<LeaveRequest> {
        val employee = employeeService.getEmployee(request.approverId)

        if(employee.leaderId != null) throw NotAuthorizedException()

        return repository.getRequestForApprover(request)
    }

    suspend fun submitRequest(request: LeaveRequestDto) {
        val quotas = getQuotas(request.employeeId)
        val durationInDays = Period.between(request.startDate, request.endDate).days
        val remainingQuotas = quotas - durationInDays

        if(remainingQuotas < 0) throw InsufficientQuotasException()
        else repository.updateQuotas(request.employeeId, remainingQuotas)

        repository.submitLeaveRequest(request.toLeaveRequest(3))
    }

    suspend fun cancelRequest(request: CancelLeaveRequestDto) {
        employeeService.getEmployee(request.employeeId)
        getRequest(request.requestId)
        repository.updateRequestStatus(request.requestId, request.employeeId, 4)
    }

    suspend fun requestApproval(request: RequestApprovalDto) {
        val employee = employeeService.getEmployee(request.approverId)

        if(employee.leaderId != null) throw NotAuthorizedException()

        val leaveRequest = getRequest(request.requestId)
        val status = if(request.approved) 1 else 2

        if(leaveRequest.status == 3) repository.updateRequestStatus(leaveRequest.id!!, leaveRequest.employee, status)
        else throw ApprovalException("Failed to approve. Request not pending")
    }

    private fun LeaveRequestDto.toLeaveRequest(status: Int): LeaveRequest {
        return LeaveRequest(
            null,
            this.startDate,
            this.endDate,
            getCurrentDate(),
            this.detail,
            this.employeeId,
            this.type,
            status
        )
    }
}