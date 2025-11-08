package com.angelhack.babycaremall.backend.module.ai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import kotlin.collections.map


@Component
class RagInitializer(
    private val vectorStore: VectorStore,
    private val embeddingClient: EmbeddingClient,
    @Value("classpath:/prompts/manual.pdf") private val manualPdf: Resource
) {
    // 애플리케이션 시작 시 한번만 호출 (적재/업데이트 파이프라인은 환경에 맞게 구성)
    @jakarta.annotation.PostConstruct
    fun loadPdf() {
        val docs: List<Document> = PagePdfDocumentReader(
            manualPdf,
            PdfDocumentReaderConfig.builder().withPagesPerDocument(1).build()
        ).get()

        // 임베딩 후 벡터스토어에 업서트
        vectorStore.add(docs.map { it.withEmbedding(embeddingClient.embed(it.content)) })
    }
}
