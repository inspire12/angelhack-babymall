package com.angelhack.babycaremall.backend.module.ai.dto

import com.angelhack.babycaremall.const.MessageRole

data class ChatRequest(
    val content: String,
    val context: String? = null,
    val sessionId: String? = null
)

data class ChatResponse(
    val id: Long?,
    val role: MessageRole,
    val content: String,
    val context: String? = null,
    val created: Long = System.currentTimeMillis(),
    val sessionId: String? = null
)

data class ProductRecommendationRequest(
    val category: String,
    val budget: String? = null,
    val ageGroup: String? = null,
    val sessionId: String? = null
)