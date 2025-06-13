package com.example.employee.leave.request.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RequestApprovalDto(
    @JsonProperty("approver_id")
    val approverId: Int,
    @JsonProperty("request_id")
    val requestId: Int,
    val approved: Boolean
)
