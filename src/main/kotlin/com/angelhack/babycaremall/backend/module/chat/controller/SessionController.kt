package com.angelhack.babycaremall.backend.module.chat.controller

import com.angelhack.babycaremall.backend.module.chat.dto.SessionRequest
import com.angelhack.babycaremall.backend.module.chat.model.Message
import com.angelhack.babycaremall.backend.module.chat.model.Session
import com.angelhack.babycaremall.backend.module.chat.service.SessionService
import org.springframework.web.bind.annotation.CrossOrigin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = ["http://localhost:3000"])
class SessionController(
    val sessionService: SessionService,
) {
    private val userId ="a0e99a9c-790d-44aa-aa7d-6e9cc6b614d0"
    @GetMapping
    fun sessions(): List<Session> {
        return sessionService.sessions(userId)
    }

    @GetMapping("/{sessionId}/messages")
    fun messages(
        @PathVariable sessionId: String,
        lastMessageId: Long? = null,
    ): List<Message> {
        // skip validation for sessionid owned by user
        return sessionService.messages(sessionId, lastMessageId)
    }

    @PostMapping()
    fun createSession(@RequestBody(required = false) session: SessionRequest
    ): Session {
        // skip validation for sessionid owned by user
        return sessionService.saveSession(userId, session.title)
    }

}