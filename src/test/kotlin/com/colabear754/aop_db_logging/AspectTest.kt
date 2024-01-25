package com.colabear754.aop_db_logging

import com.colabear754.aop_db_logging.log.repository.ApiLogRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
class AspectTest @Autowired constructor(
    private val apiLogRepository: ApiLogRepository,
    private val mock: MockMvc
) {
    @Transactional
    @Test
    fun `AOP 로그 테스트`() {
        // given
        // when
        mock.perform(post("/post?message=hello", "{\"name\":\"colabear754\"}"))
        // then
        val logs = apiLogRepository.findAll()
        logs.forEach { println(it) }
    }

    private fun get(url: String) = MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
    private fun post(url: String, body: String) = MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(body)
}