package com.colabear754.aop_db_logging.log.repository

import com.colabear754.aop_db_logging.log.entity.ApiLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ApiLogRepository : JpaRepository<ApiLog, Long> {
    @Transactional
    @Modifying
    @Query("""
        UPDATE ApiLog a
        SET a.responseStatus = :status, a.response = :response, a.responseTime = CURRENT_TIMESTAMP
        WHERE a.seq = :seq
    """)
    fun updateResponse(seq: Long, status: Int, response: String)
}