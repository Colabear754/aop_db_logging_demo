package com.colabear754.aop_db_logging.controllers

import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Transactional
@RestController
class TestController {
    @GetMapping("/200ok")
    fun ok() = ResponseEntity.ok("ok")

    @GetMapping("/400bad")
    fun bad() = ResponseEntity.badRequest().body("bad")

    @GetMapping("/500error")
    fun error() = ResponseEntity.status(500).body("error")

    @PostMapping("/post")
    fun post(message: String, @RequestBody body: Map<String, String>) = ResponseEntity.ok(body.toMutableMap().apply { put("message", message) })
}
