package com.colabear754.aop_db_logging.aspect

import com.colabear754.aop_db_logging.log.entity.ApiLog
import com.colabear754.aop_db_logging.log.repository.ApiLogRepository
import com.colabear754.aop_db_logging.log.util.exceptionToStatus
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.net.InetAddress

@Aspect
@Component
class AopLogging(
    private val apiLogRepository: ApiLogRepository
) {
    @Around("execution(public * com.colabear754.aop_db_logging.controllers.*.*(..))")
    fun log(joinPoint: ProceedingJoinPoint): Any? {
        val requestAttributes = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        val httpRequest = requestAttributes.request
        val log = apiLogRepository.save(
            ApiLog(
                serverIp = InetAddress.getLocalHost().hostAddress,
                requestUrl = httpRequest.requestURL.toString(),
                requestMethod = httpRequest.method,
                clientIp = httpRequest.getHeader("X-FORWARDED-FOR")?.takeUnless { it.isBlank() } ?: httpRequest.remoteAddr,
                request = getRequestString(joinPoint)
            )
        )
        try {
            val response = joinPoint.proceed() as ResponseEntity<*>
            apiLogRepository.updateResponse(log.seq!!, response.statusCode.value(), response.body.toString())
            return response
        } catch (e: Exception) {
            apiLogRepository.updateResponse(log.seq!!, exceptionToStatus(e).value(), e.message ?: "error")
            throw e
        }
    }

    private fun getRequestString(joinPoint: ProceedingJoinPoint): String {
        val signature = joinPoint.signature
        val parameterNames = if (signature is MethodSignature) signature.parameterNames else return ""
        val parameterValues = joinPoint.args
        return parameterNames.zip(parameterValues).joinToString(", ") { "${it.first}=${it.second}" }
    }
}