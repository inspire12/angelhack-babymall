package com.angelhack.babycaremall.chat.service

import com.angelhack.babycaremall.chat.model.Message
import com.angelhack.babycaremall.chat.model.Session
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class SessionService(
    val mongoTemplate: MongoTemplate
) {

    fun sessions(userId: String): List<Session> {
        return mongoTemplate.find(
            Query.query(Criteria.where("userId").`is`(userId)),
            Session::class.java)
    }

    fun messages(sessionId: String, lastMessageId: Long?): List<Message> {
        return mongoTemplate.find(
            Query.query(Criteria.where("sessionId").`is`(sessionId)
                .let {
                    if (lastMessageId != null) it.and("messageId").gt(lastMessageId)
                    else it
                }
            ),
            Message::class.java)
    }
}