package com.angelhack.babycaremall.backend.module.ai.service

import com.angelhack.babycaremall.backend.module.ai.dto.ChatContext
import com.angelhack.babycaremall.backend.module.ai.dto.ChatResponse
import com.angelhack.babycaremall.const.MessageRole
import com.angelhack.babycaremall.chat.model.Message
import com.angelhack.babycaremall.chat.model.Session
import com.angelhack.babycaremall.const.MESSAGE_SEQUENCE_NAME
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID


@Service
class AiChatService(
    private val sequenceService: SequenceService,
    private val chatClient: ChatClient.Builder,
    private val mongoTemplate: MongoTemplate,
    private val promptRagService: PromptRagService,
    private val chatMemory : InMemoryChatMemory = InMemoryChatMemory()
) {
    private val userId = UUID.fromString("a0e99a9c-790d-44aa-aa7d-6e9cc6b614d0")
    private val client = chatClient.build()

    fun generateResponse(
        userId: String,
        userMessage: String,
        context: String? = null,
        sessionId: String? = null,
    ): ChatResponse {
        val isNewSession = sessionId == null
        val sid: String = sessionId ?: UUID.randomUUID().toString()

        val memoryMessage = if (!isNewSession) chatMemory.get(sessionId, 5) else listOf()

        val fullPrompt = promptRagService.getPrompt(memoryMessage, userMessage, context, sid)
        val answer: String = client.prompt()
            .user(fullPrompt)
            .call()
            .content() ?: "죄송합니다. 응답을 생성할 수 없습니다."

        chatMemory.add(sid, UserMessage(userMessage))
        chatMemory.add(sid, AssistantMessage(answer))

        if (isNewSession) mongoTemplate.insert(Session(
            null,
            sid,
            userId = userId,
            createdAt = Instant.now(),
        ))

        val promptId = sequenceService.generateSequence(MESSAGE_SEQUENCE_NAME)
        mongoTemplate.insert(Message(
            null,
            sessionId=sid,
            promptId,
            content=userMessage,
            MessageRole.USER,
            Instant.now(),
        ))

        val responseId = sequenceService.generateSequence(MESSAGE_SEQUENCE_NAME)
        val responseAt = Instant.now()
        mongoTemplate.insert(Message(
            null,
            sid,
            responseId,
            answer,
            MessageRole.SYSTEM,
            responseAt,
        ))

        return ChatResponse(
            responseId,
            MessageRole.SYSTEM,
            answer,
            null,
            responseAt.toEpochMilli(),
            sid
        )
    }

    fun generateProductRecommendation(userId: String, category: String, budget: String?, ageGroup: String?): ChatResponse {
        val prompt = buildString {
            append("다음 조건에 맞는 아기용품을 추천해주세요:\n")
            append("카테고리: $category\n")
            budget?.let { append("예산: $it\n") }
            ageGroup?.let { append("아기 연령: $it\n") }
            append("구체적인 제품명과 특징, 가격대를 포함해서 3-5개 정도 추천해주세요.")
        }

        return generateResponse(userId, prompt)
    }
}