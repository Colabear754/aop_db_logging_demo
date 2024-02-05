package com.colabear754.aop_db_logging.log.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ApiLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long? = null,
    val serverIp: String,
    @Column(length = 4096)
    val requestUrl: String,
    val requestMethod: String,
    var responseStatus: Int? = null,
    val clientIp: String,
    @Column(length = 4096)
    val request: String,
    @Column(length = 4096)
    var response: String? = null,
    val requestTime: LocalDateTime = LocalDateTime.now(),
    var responseTime: LocalDateTime?= null
)