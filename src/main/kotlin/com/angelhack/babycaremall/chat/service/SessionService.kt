package com.angelhack.babycaremall.chat.service

import com.angelhack.babycaremall.chat.model.Message
import com.angelhack.babycaremall.chat.model.Session
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.*

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
        return if (lastMessageId != null) mongoTemplate.find(
            Query.query(Criteria.where("messageId").gt(lastMessageId)
                .and("sessionId").`is`(sessionId)),
            Message::class.java)
        else mongoTemplate.findAll(Message::class.java)
    }
}