package com.angelhack.babycaremall.backend.module.ai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Service

@Service
class AiChatService(
    private val chatClient: ChatClient.Builder,
    private val chatModel: ChatModel
) {
    
    private val client = chatClient.build()
    
    fun generateResponse(userMessage: String, context: String? = null): String {
        val systemPrompt = """
            당신은 육아용품몰의 친절한 AI 어시스턴트입니다.
            아기용품, 육아 상담, 제품 추천에 대해 도움을 드릴 수 있습니다.
            항상 친근하고 도움이 되는 답변을 제공해주세요.
        """.trimIndent()
        
        val fullPrompt = if (context != null) {
            "$systemPrompt\n\n컨텍스트: $context\n\n사용자 질문: $userMessage"
        } else {
            "$systemPrompt\n\n사용자 질문: $userMessage"
        }
        
        return client.prompt()
            .user(fullPrompt)
            .call()
            .content() ?: "죄송합니다. 응답을 생성할 수 없습니다."
    }
    
    fun generateProductRecommendation(category: String, budget: String?, ageGroup: String?): String {
        val prompt = buildString {
            append("다음 조건에 맞는 아기용품을 추천해주세요:\n")
            append("카테고리: $category\n")
            budget?.let { append("예산: $it\n") }
            ageGroup?.let { append("아기 연령: $it\n") }
            append("구체적인 제품명과 특징, 가격대를 포함해서 3-5개 정도 추천해주세요.")
        }
        
        return generateResponse(prompt)
    }
}