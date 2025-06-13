package com.example.employee.leave.request.utils

import io.micrometer.core.instrument.kotlin.asContextElement
import io.micrometer.observation.Observation
import kotlinx.coroutines.withContext

suspend fun <T> Observation.observeSuspend(block: suspend() -> T): T {
    return withContext(observationRegistry.asContextElement()) {
        try {
            start()
            block()
        } catch (e: Throwable) {
            error(e)

            throw e
        } finally {
            stop()
        }
    }
}