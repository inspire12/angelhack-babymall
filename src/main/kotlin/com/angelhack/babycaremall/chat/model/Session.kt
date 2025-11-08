package com.angelhack.babycaremall.chat.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection ="sessions")
data class Session (
    @Id
    var id: ObjectId? = null,
    var sessionId: String,
    var userId: String,
    var createdAt: Instant,
)
