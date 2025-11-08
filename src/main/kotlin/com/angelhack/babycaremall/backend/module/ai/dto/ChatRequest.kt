package com.angelhack.babycaremall.backend.module.ai.dto

import com.angelhack.babycaremall.chat.model.Message
import com.angelhack.babycaremall.const.MessageRole

data class ChatRequest(
    val content: String,
    val context: String? = null,
    val sessionId: String? = null
)

data class ChatResponse(
    val message: Message,
    val sentMessage: Message,
)

data class ProductRecommendationRequest(
    val category: String,
    val budget: String? = null,
    val ageGroup: String? = null,
    val sessionId: String? = null
)