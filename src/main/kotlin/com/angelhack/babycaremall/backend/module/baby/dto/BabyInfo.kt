package com.angelhack.babycaremall.backend.module.baby.dto


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "babies")
data class BabyInfo(
    @Id
    val id: String? = null,
    val name: String,
    val gender: Gender,
    val birthDate: String,
    val months: Int,
    val profileImageUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class Gender {
    MALE, FEMALE, UNKNOWN
}