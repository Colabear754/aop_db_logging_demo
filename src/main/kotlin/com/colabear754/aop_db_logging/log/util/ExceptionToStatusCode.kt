package com.colabear754.aop_db_logging.log.util

import org.springframework.http.HttpStatus

fun exceptionToStatus(e: Exception): HttpStatus {
    return when (e) {
        is IllegalArgumentException -> HttpStatus.BAD_REQUEST
        is AccessDeniedException -> HttpStatus.FORBIDDEN
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}