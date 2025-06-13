package com.example.employee.leave.request.configuration

import com.example.employee.leave.request.handler.EmployeeHandler
import com.example.employee.leave.request.handler.LeaveRequestHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {
    @Bean
    fun mainRoute(employeeHandler: EmployeeHandler, leaveRequestHandler: LeaveRequestHandler) = coRouter {
        "/api/employee".nest {
            GET("/{id}", employeeHandler::getEmployee)

            "/leave".nest {
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
                GET("/quotas/{id}")
                GET("/types")
                GET("/statuses")
            }
        }
    }
}