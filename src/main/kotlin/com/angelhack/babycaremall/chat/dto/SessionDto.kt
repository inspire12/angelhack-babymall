package com.angelhack.babycaremall.chat.dto

import com.angelhack.babycaremall.const.MessageRole
import java.time.Instant

data class SessionDto(
    val sessionId: String,
    val messageId: Instant,
    val content: String,
    val role: MessageRole,
    val createdAt: String,
)
