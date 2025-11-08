package com.angelhack.babycaremall.backend.module.ai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service


@Service
class AiServiceRag(private val chatClient: ChatClient, private val vectorStore: VectorStore) {
    fun generateResponse(userMessage: String, context: String? = null): String? {
        val advisor = QuestionAnswerAdvisor(
            vectorStore,
            SearchRequest.query(userMessage).withTopK(5)
        )

        val userCombined = if (context != null)
            "컨텍스트: $context\n\n사용자 질문: $userMessage"
        else userMessage

        return chatClient.prompt()
            .system { it.text("당신은 육아용품몰의 친절한 AI 어시스턴트입니다. 검색된 레퍼런스만 근거로 답하세요.") }
            .advisors(advisor) // 관련 PDF 조각이 자동으로 프롬프트에 합쳐짐
            .user(userCombined)
            .call()
            .content()
    }
}