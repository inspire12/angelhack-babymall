package com.angelhack.babycaremall.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.util.UUID

@Configuration
class MongoConfig {

    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        // 코틀린의 listOf()를 사용해 컨버터 목록을 생성합니다.
        val converters = listOf(
            UuidToStringConverter, // 아래 정의된 object 인스턴스
            StringToUuidConverter  // 아래 정의된 object 인스턴스
        )
        return MongoCustomConversions(converters)
    }
}

/**
 * 1. UUID -> String 컨버터
 * 'object' 키워드를 사용해 싱글톤 객체로 선언합니다.
 */
object UuidToStringConverter : Converter<UUID, String> {
    override fun convert(uuid: UUID): String? {
        // 코틀린의 safe call (?.)을 사용해
        // uuid가 null이면 null을, 아니면 toString()을 반환합니다.
        return uuid.toString()
    }
}

/**
 * 2. String -> UUID 컨버터
 * 'object' 키워드를 사용해 싱글톤 객체로 선언합니다.
 */
object StringToUuidConverter : Converter<String, UUID> {
    override fun convert(source: String): UUID? {
        // 'let' 함수와 safe call을 사용해
        // source가 null이 아닐 경우에만 UUID.fromString()을 실행합니다.
        return source.let { UUID.fromString(it) }
    }
}