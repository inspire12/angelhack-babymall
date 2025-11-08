package com.angelhack.babycaremall.backend.module.ai.service

import com.angelhack.babycaremall.backend.module.babydiary.dto.BabyDiary
import org.apache.coyote.http11.Constants.a
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import kotlin.collections.joinToString

@Service
class PromptRagService(

    private val vectorStore: VectorStore,
) {

    fun getPrompt(allDiaries: List<BabyDiary>, memoryMessage: List<Message>, userMessage: String, context: String? = null, sessionId: String): String {
        val hits = vectorStore.similaritySearch(
            userMessage
        )
        val ctx = hits.joinToString("\n\n") { "- ${it.content}" }

        val systemPrompt = """
        당신은 육아용품몰의 친절한 AI 어시스턴트입니다.
        아기용품, 육아 상담, 제품 추천에 대해 도움을 드릴 수 있습니다.
        항상 친근하고 도움이 되는 답변을 제공해주세요.
    """.trimIndent()
        val diariesTextList = allDiaries.stream().map { diary -> (diary.date + " " + diary.preview)}.toList()
        val diariesText = diariesTextList.joinToString { text -> text }
        val message = mutableListOf<Message>()
        message+= memoryMessage
        message+= UserMessage(diariesText)
        message+= UserMessage(userMessage)
        val fullPrompt = buildString {
            appendLine(systemPrompt)
            context?.let { appendLine("\n컨텍스트: $it") }
            appendLine("\n검색 컨텍스트:\n$ctx")
            appendLine("\n사용자 질문: $message")
            appendLine("\n사용자 문맥: $sessionId")
        }

        return fullPrompt
    }
}