package com.angelhack.babycaremall.backend.module.shopping.domain.service

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document("users")
data class User(
    @Id val id: String? = null,
    val name: String,
    val age: Int
)
