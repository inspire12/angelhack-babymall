package com.angelhack.babycaremall.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.embedding.Embedding
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class AiConfig(
    private val vectorStore: VectorStore,
) {

    @Bean
    fun chatClient(@Qualifier("openAiChatModel") chatModel: ChatModel): ChatClient.Builder {
        return ChatClient.builder(chatModel)
    }


    @Bean
    fun ingestPdf(@Value("classpath:prompts/manual.pdf") manualPdf: Resource): ApplicationRunner = ApplicationRunner {

        // 1) 'manual.pdf'가 이미 벡터스토어에 있나 간단히 확인
        val alreadyIndexed = vectorStore
            .similaritySearch(
                SearchRequest
                    .query("ping")
                    .withTopK(1)
                    .withFilterExpression("""source == "manual.pdf" """)
            )
            .isNotEmpty()

        if (alreadyIndexed) return@ApplicationRunner  // ❗이미 있으면 스킵


        val docs = PagePdfDocumentReader(
            manualPdf,
            PdfDocumentReaderConfig.builder().withPagesPerDocument(1).build()
        ).get()

        // ❌ 전량 join 금지! -> ✅ 토큰 기준 청크로 쪼개기
        val splitter = TokenTextSplitter( // 토큰 기준 분할로 과토큰 방지
            800,  // chunkSize (모델에 따라 500~1200 권장)
            120,   // chunkOverlap (문맥 손실 방지)
            5,
            10000,
            false
        )
        val chunks = splitter.apply(docs)

        // 메타데이터 넣어두면 검색 품질 개선
        val enriched = chunks.map { it.apply { metadata["source"] = "manual.pdf" } }

        // 임베딩 생성은 VectorStore.add 내부에서 embeddingClient 사용
        vectorStore.add(enriched)
    }
}