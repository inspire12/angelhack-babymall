package com.angelhack.babycaremall.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiConfig {
    
    @Bean
    fun chatClient(@Qualifier("openAiChatModel") chatModel: ChatModel): ChatClient.Builder {
        return ChatClient.builder(chatModel)
    }
}