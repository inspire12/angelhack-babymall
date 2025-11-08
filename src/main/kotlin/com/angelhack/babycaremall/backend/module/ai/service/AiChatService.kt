package com.angelhack.babycaremall.backend.module.ai.service

import com.angelhack.babycaremall.backend.module.ai.dto.ChatContext
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class AiChatService(
    private val chatClient: ChatClient.Builder,
    private val promptRagService: PromptRagService,
    private val chatMemory : InMemoryChatMemory = InMemoryChatMemory()
) {

    private val client = chatClient.build()

    fun generateResponse(
        userMessage: String,
        context: String? = null,
        sessionId: String? = null,
    ): ChatContext {
        val sid: String = sessionId ?: UUID.randomUUID().toString()


        val memoryMessage = chatMemory.get(sessionId, 5)

        val fullPrompt = promptRagService.getPrompt(memoryMessage, userMessage, context, sid)
        val answer: String = client.prompt()
            .user(fullPrompt)
            .call()
            .content() ?: "죄송합니다. 응답을 생성할 수 없습니다."

        chatMemory.add(sid, UserMessage(userMessage))
        chatMemory.add(sid, AssistantMessage(answer))

        return ChatContext(answer, sid)
    }

    fun generateProductRecommendation(category: String, budget: String?, ageGroup: String?): ChatContext {
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