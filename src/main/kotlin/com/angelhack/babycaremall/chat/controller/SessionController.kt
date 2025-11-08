package com.angelhack.babycaremall.chat.controller

import com.angelhack.babycaremall.chat.model.Message
import com.angelhack.babycaremall.chat.model.Session
import com.angelhack.babycaremall.chat.service.SessionService
import org.springframework.data.domain.Page

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sessions")
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
}