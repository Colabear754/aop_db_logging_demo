package com.colabear754.aop_db_logging

import com.colabear754.aop_db_logging.log.repository.ApiLogRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@AutoConfigureMockMvc
class AspectTest @Autowired constructor(
    private val apiLogRepository: ApiLogRepository,
    private val mock: MockMvc
) {
    @BeforeEach
    fun reset() = apiLogRepository.deleteAllInBatch()

    @Test
    fun `GET 로그 테스트`() {
        // given
        // when
        mock.perform(get("/200ok?name=colabear754&message=hello").contentType(MediaType.APPLICATION_JSON))
        // then
        val log = apiLogRepository.findAll()[0]
        assertThat(log.request).isEqualTo("name=colabear754, message=hello")
        assertThat(log.responseStatus).isEqualTo(200)
        assertThat(log.serverIp).isEqualTo("192.168.0.120")
        assertThat(log.requestUrl).isEqualTo("http://localhost/200ok")
        assertThat(log.requestMethod).isEqualTo("GET")
        assertThat(log.clientIp).isEqualTo("127.0.0.1")
        assertThat(log.response).isEqualTo("{name=colabear754, message=hello}")
    }

    @Test
    fun `BAD REQUEST 로그 테스트`() {
        // given
        // when
        mock.perform(get("/400bad?name=ciderbear754&message=hi").contentType(MediaType.APPLICATION_JSON))
        // then
        val log = apiLogRepository.findAll()[0]
        assertThat(log.request).isEqualTo("name=ciderbear754, message=hi")
        assertThat(log.responseStatus).isEqualTo(400)
        assertThat(log.serverIp).isEqualTo("192.168.0.120")
        assertThat(log.requestUrl).isEqualTo("http://localhost/400bad")
        assertThat(log.requestMethod).isEqualTo("GET")
        assertThat(log.clientIp).isEqualTo("127.0.0.1")
        assertThat(log.response).isEqualTo("입력값을 확인해주세요.")
    }

    @Test
    fun `INTERNAL SERVER ERROR 로그 테스트`() {
        // given
        // when
        mock.perform(get("/500error?name=fantabear754&message=yes").contentType(MediaType.APPLICATION_JSON))
        // then
        val log = apiLogRepository.findAll()[0]
        assertThat(log.request).isEqualTo("name=fantabear754, message=yes")
        assertThat(log.responseStatus).isEqualTo(500)
        assertThat(log.serverIp).isEqualTo("192.168.0.120")
        assertThat(log.requestUrl).isEqualTo("http://localhost/500error")
        assertThat(log.requestMethod).isEqualTo("GET")
        assertThat(log.clientIp).isEqualTo("127.0.0.1")
        assertThat(log.response).isEqualTo("서버 에러가 발생했습니다.")
    }

    @Test
    fun `POST 로그 테스트`() {
        // given
        // when
        mock.perform(post("/post?message=hello").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"colabear754\"}"))
        // then
        val log = apiLogRepository.findAll()[0]
        assertThat(log.request).isEqualTo("message=hello, body={name=colabear754}")
        assertThat(log.responseStatus).isEqualTo(200)
        assertThat(log.serverIp).isEqualTo("192.168.0.120")
        assertThat(log.requestUrl).isEqualTo("http://localhost/post")
        assertThat(log.requestMethod).isEqualTo("POST")
        assertThat(log.clientIp).isEqualTo("127.0.0.1")
        assertThat(log.response).isEqualTo("{name=colabear754, message=hello}")
    }
}