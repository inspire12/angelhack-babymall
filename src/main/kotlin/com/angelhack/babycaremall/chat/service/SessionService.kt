package com.angelhack.babycaremall.chat.service

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

    fun sessions(userId: UUID): List<Session> {
        return mongoTemplate.find(
            Query.query(Criteria.where("userId").`is`(userId)),
            Session::class.java, "sessions")
    }
}