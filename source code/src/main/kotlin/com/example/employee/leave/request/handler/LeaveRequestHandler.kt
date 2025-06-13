package com.example.employee.leave.request.handler

import com.example.employee.leave.request.dto.*
import com.example.employee.leave.request.service.LeaveRequestService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class LeaveRequestHandler(
    private val service: LeaveRequestService
) {
    suspend fun getLeaveTypes(serverRequest: ServerRequest): ServerResponse {
        val result = service.getLeaveTypes()

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getLeaveRequestStatuses(serverRequest: ServerRequest): ServerResponse {
        val result = service.getLeaveRequestStatuses()

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getQuotas(serverRequest: ServerRequest): ServerResponse {
        val id = serverRequest.pathVariable("id").toInt()
        val result = service.getQuotas(id)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getRequest(serverRequest: ServerRequest): ServerResponse {
        val body = serverRequest.awaitBody<GetLeaveRequestDto>()
        val result = service.getRequests(body)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getRequestForApprover(serverRequest: ServerRequest): ServerResponse {
        val body = serverRequest.awaitBody<GetLeaveRequestForApproverDto>()
        val result = service.getRequestForApprover(body)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun submitRequest(serverRequest: ServerRequest): ServerResponse {
        val body = serverRequest.awaitBody<LeaveRequestDto>()

        service.submitRequest(body)

        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun cancelRequest(serverRequest: ServerRequest): ServerResponse {
        val body = serverRequest.awaitBody<CancelLeaveRequestDto>()

        service.cancelRequest(body)

        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun requestApproval(serverRequest: ServerRequest): ServerResponse {
        val body = serverRequest.awaitBody<RequestApprovalDto>()

        service.requestApproval(body)

        return ServerResponse.noContent().buildAndAwait()
    }
}