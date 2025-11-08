package com.angelhack.babycaremall.backend.module.chat.service

import com.angelhack.babycaremall.backend.module.chat.model.Message
import com.angelhack.babycaremall.backend.module.chat.model.Session
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class SessionService(
    val mongoTemplate: MongoTemplate,
) {

    fun sessions(userId: String): List<Session> {
        return mongoTemplate.find(
            Query.query(Criteria.where("userId").`is`(userId)),
            Session::class.java
        )
    }

    fun messages(sessionId: String, lastMessageId: Long?): List<Message> {
        return mongoTemplate.find(
            Query.query(
                Criteria.where("sessionId").`is`(sessionId)
                    .let {
                        if (lastMessageId != null) it.and("messageId").gt(lastMessageId)
                        else it
                    }
            ),
            Message::class.java)
    }

    fun saveSession(userId: String, title: String? = null): Session {
        val sid = UUID.randomUUID().toString()
        return mongoTemplate.insert(
            Session(
                null,
                sid,
                title = title,
                userId = userId,
                createdAt = Instant.now(),
            )
        )
    }

}