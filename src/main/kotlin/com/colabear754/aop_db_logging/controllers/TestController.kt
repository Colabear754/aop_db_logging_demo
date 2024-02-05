package com.colabear754.aop_db_logging.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/200ok")
    fun ok(name: String, message: String) = ResponseEntity.ok(mapOf("name" to name, "message" to message))

    @GetMapping("/400bad")
    fun bad(name: String, message: String): Nothing = throw IllegalArgumentException("입력값을 확인해주세요.")

    @GetMapping("/500error")
    fun error(name: String, message: String): Nothing = throw RuntimeException("서버 에러가 발생했습니다.")

    @PostMapping("/post")
    fun post(message: String, @RequestBody body: Map<String, String>) = ResponseEntity.ok(body.toMutableMap().apply { put("message", message) })
}
