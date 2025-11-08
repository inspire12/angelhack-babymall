package com.angelhack.babycaremall.backend.module.ai.service

import com.angelhack.babycaremall.backend.module.ai.dto.DatabaseSequence
import org.springframework.data.mongodb.core.FindAndModifyOptions.options
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update

import org.springframework.stereotype.Service

@Service
class SequenceService(
    private val mongoTemplate: MongoTemplate,
) {
    fun generateSequence(seqName: String): Long {

        // 1. 쿼리: id가 seqName과 일치하는 문서를 찾습니다.
        val query = Query(Criteria.where("_id").`is`(seqName))

        // 2. 업데이트: "seq" 필드의 값을 1 증가시킵니다.
        val update = Update().inc("seq", 1)

        // 3. 옵션:
        //    - returnNew(true): 업데이트된 *이후*의 문서를 반환받습니다.
        //    - upsert(true): 만약 문서를 찾지 못했다면(최초 실행 시),
        //                   쿼리와 업데이트를 조합하여 새 문서를 생성합니다.
        val options = options().returnNew(true).upsert(true)

        // 4. 원자적 연산 실행:
        //    findAndModify는 이 모든 과정을 하나의 원자적 작업으로 처리합니다.
        val counter = mongoTemplate.findAndModify(
            query,
            update,
            options,
            DatabaseSequence::class.java // 반환받을 타입
        )

        // 5. 결과 반환:
        //    upsert(true)를 사용했으므로 counter가 null이 될 수 없습니다.
        //    하지만 안전하게 처리하기 위해 null일 경우 1을 반환 (최초 생성 시)
        return counter?.seq ?: 1
    }
}