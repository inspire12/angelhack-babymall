package com.angelhack.babycaremall.chat.model

import com.angelhack.babycaremall.const.MessageRole
import org.bson.types.ObjectId
import java.time.Instant

data class Session (
    val id: ObjectId,
    val sessionId: String,
    val messageId: Instant,
    val content: String,
    val role: MessageRole,
    val createdAt: String,
)
