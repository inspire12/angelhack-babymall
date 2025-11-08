package com.angelhack.babycaremall.backend.module.ai.dto

data class ChatRequest(
    val message: String,
    val context: String? = null,
    val sessionId: String? = null
)

data class ChatResponse(
    val response: String,
    val timestamp: Long = System.currentTimeMillis(),
    val sessionId: String? = null
)

data class ProductRecommendationRequest(
    val category: String,
    val budget: String? = null,
    val ageGroup: String? = null
)