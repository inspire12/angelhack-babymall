package com.angelhack.babycaremall.backend.module.ai.service

import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import kotlin.collections.joinToString

@Service
class PromptRagService (

    private val vectorStore: VectorStore,
){

    fun getPrompt(userMessage: String, context: String? = null) : String {
        val hits = vectorStore.similaritySearch(userMessage)
        val ctx = hits.joinToString("\n\n") { "- ${it.content}" }

        val systemPrompt = """
        당신은 육아용품몰의 친절한 AI 어시스턴트입니다.
        아기용품, 육아 상담, 제품 추천에 대해 도움을 드릴 수 있습니다.
        항상 친근하고 도움이 되는 답변을 제공해주세요.
    """.trimIndent()

        val fullPrompt = buildString {
            appendLine(systemPrompt)
            context?.let { appendLine("\n컨텍스트: $it") }
            appendLine("\n검색 컨텍스트:\n$ctx")
            appendLine("\n사용자 질문: $userMessage")
        }

        return fullPrompt
    }
}