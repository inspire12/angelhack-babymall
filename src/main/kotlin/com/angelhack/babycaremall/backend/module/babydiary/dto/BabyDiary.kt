package com.angelhack.babycaremall.backend.module.babydiary.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "baby_diaries")
data class BabyDiary(
    @Id
    val id: String? = null,
    val title: String,
    val date: String,
    val preview: String,
    val image: String
)