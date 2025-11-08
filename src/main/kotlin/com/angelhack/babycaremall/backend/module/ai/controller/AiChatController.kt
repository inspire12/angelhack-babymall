package com.angelhack.babycaremall.backend.module.ai.controller

import com.angelhack.babycaremall.backend.module.ai.dto.ChatRequest
import com.angelhack.babycaremall.backend.module.ai.dto.ChatResponse
import com.angelhack.babycaremall.backend.module.ai.dto.ProductRecommendationRequest
import com.angelhack.babycaremall.backend.module.ai.service.AiChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = ["http://localhost:3000"])
class AiChatController(
    private val aiChatService: AiChatService
) {
    private val userId ="a0e99a9c-790d-44aa-aa7d-6e9cc6b614d0"

    @PostMapping("/chat")
    fun chat(@RequestBody request: ChatRequest): ResponseEntity<ChatResponse> {
        return try {

            val response = aiChatService.generateResponse(userId, request.message, request.context, request.sessionId)
            ResponseEntity.ok(
                ChatResponse(
                    response = response.text,
                    sessionId = response.sessionId
                )
            )
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(
                ChatResponse(
                    response = "죄송합니다. 오류가 발생했습니다: ${e.message}",
                    sessionId = request.sessionId
                )
            )
        }
    }


    @PostMapping("/recommend")
    fun recommendProducts(@RequestBody request: ProductRecommendationRequest): ResponseEntity<ChatResponse> {
        return try {
            val response = aiChatService.generateProductRecommendation(
                userId,
                category = request.category,
                budget = request.budget,
                ageGroup = request.ageGroup
            )
            ResponseEntity.ok(ChatResponse(response = response.text, sessionId = response.sessionId))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(
                ChatResponse(response = "제품 추천 중 오류가 발생했습니다: ${e.message}")
            )
        }
    }
    
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf("status" to "AI service is running"))
    }
}