package com.colabear754.aop_db_logging.log.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ApiLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val seq: Long? = null,
    private val serverIp: String,
    @Column(length = 4096)
    private val requestUrl: String,
    private val requestMethod: String,
    private var responseStatus: Int? = null,
    private val clientIp: String,
    @Column(length = 4096)
    private val request: String,
    @Column(length = 4096)
    private var response: String? = null,
    private val requestTime: LocalDateTime,
    private var responseTime: LocalDateTime?= null
) {
    fun receiveResponse(responseStatus: Int, responseBody: String) {
        this.responseStatus = responseStatus
        this.response = responseBody
        this.responseTime = LocalDateTime.now()
    }

    override fun toString(): String {
        return "ApiLog(seq=$seq, serverIp='$serverIp', requestUrl='$requestUrl', requestMethod='$requestMethod', responseStatus=$responseStatus, clientIp='$clientIp', request='$request', response=$response, requestTime=$requestTime, responseTime=$responseTime)"
    }
}