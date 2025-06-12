package com.example.employee.leave.request.configuration

import com.example.employee.leave.request.handler.LeaveRequestHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {
    @Bean
    fun mainLeaveRequestRoute(handler: LeaveRequestHandler) = coRouter {
        "/api/employee/leave".nest {
            "/request".nest {
                POST("")
                POST("/cancel")
                GET("/pending")
                GET("/approved")
            }
            "/approval".nest {
                POST("")
                GET("/pending")
                GET("/approved")
            }
        }
    }
}