package com.colabear754.aop_db_logging.log.repository

import com.colabear754.aop_db_logging.log.entity.ApiLog
import org.springframework.data.jpa.repository.JpaRepository

interface ApiLogRepository : JpaRepository<ApiLog, Long>