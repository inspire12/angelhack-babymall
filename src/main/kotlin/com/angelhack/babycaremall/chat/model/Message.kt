package com.angelhack.babycaremall.chat.model

import com.angelhack.babycaremall.const.MessageRole
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID

@Document(collection ="messages")
data class Message(
    @Id
    var id: String? = null,
    var sessionId: String,
    var messageId: Long,
    var content: String,
    var role: MessageRole,
    var createdAt: Instant,
)