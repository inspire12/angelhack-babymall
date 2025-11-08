package com.angelhack.babycaremall.frontend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.concurrent.TimeUnit

@Configuration
class StaticCacheConfig {
    @Bean
    fun webMvcConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
            registry
                .addResourceHandler("/app/**")
                .addResourceLocations("classpath:/static/app/")
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic())
        }
    }
}