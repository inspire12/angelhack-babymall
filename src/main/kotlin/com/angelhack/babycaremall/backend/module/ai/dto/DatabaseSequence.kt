package com.angelhack.babycaremall.backend.module.ai.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "database_sequences")
data class DatabaseSequence(
    @Id
    val id: String, // 시퀀스의 이름 (예: "users_seq", "orders_seq")
    val seq: Long   // 현재 시퀀스 번호
)