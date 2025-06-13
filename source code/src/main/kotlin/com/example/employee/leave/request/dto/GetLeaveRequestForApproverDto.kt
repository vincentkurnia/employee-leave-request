package com.example.employee.leave.request.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GetLeaveRequestForApproverDto(
    @JsonProperty("approver_id")
    val approverId: Int,
    val status: Int
)