package com.angelhack.babycaremall.backend.module.babydiary.controller

import com.angelhack.babycaremall.backend.module.babydiary.dto.BabyDiary
import com.angelhack.babycaremall.backend.module.babydiary.service.BabyDiaryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/baby/diaries")
class BabyController(
    private val babyDiaryService: BabyDiaryService
) {

    @GetMapping
    fun getAllDiaries(): List<BabyDiary> {
        return babyDiaryService.getAllDiaries()
    }

    @GetMapping("/{id}")
    fun getDiaryById(@PathVariable id: Long): ResponseEntity<BabyDiary> {
        return babyDiaryService.getDiaryById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createDiary(@RequestBody diary: BabyDiary): ResponseEntity<BabyDiary> {
        val savedDiary = babyDiaryService.createDiary(diary)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDiary)
    }

    @PutMapping("/{id}")
    fun updateDiary(@PathVariable id: Long, @RequestBody diary: BabyDiary): ResponseEntity<BabyDiary> {
        return babyDiaryService.updateDiary(id, diary)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteDiary(@PathVariable id: Long): ResponseEntity<Void> {
        return if (babyDiaryService.deleteDiary(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}