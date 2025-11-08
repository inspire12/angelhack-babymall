package com.angelhack.babycaremall.backend.module.baby.controller

import com.angelhack.babycaremall.backend.module.baby.dto.*
import com.angelhack.babycaremall.backend.module.baby.service.BabyInfoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/baby/info")
class BabyInfoController(
    private val babyInfoService: BabyInfoService
) {

    @GetMapping
    fun getAllBabies(): List<BabyInfo> {
        return babyInfoService.getAllBabies()
    }

    @GetMapping("/{id}")
    fun getBabyById(@PathVariable id: String): ResponseEntity<BabyInfo> {
        return babyInfoService.getBabyById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createBaby(@RequestBody request: CreateBabyInfoRequest): ResponseEntity<BabyInfo> {
        val baby = BabyInfo(
            name = request.name,
            gender = request.gender,
            birthDate = request.birthDate,
            months = request.months,
            profileImageUrl = request.profileImageUrl
        )
        val savedBaby = babyInfoService.createBaby(baby)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBaby)
    }

    @PutMapping("/{id}")
    fun updateBaby(@PathVariable id: String, @RequestBody request: UpdateBabyInfoRequest): ResponseEntity<BabyInfo> {
        val updatedBaby = BabyInfo(
            id = id,
            name = request.name,
            gender = request.gender,
            birthDate = request.birthDate,
            months = request.months,
            profileImageUrl = request.profileImageUrl
        )
        
        return babyInfoService.updateBaby(id, updatedBaby)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteBaby(@PathVariable id: String): ResponseEntity<Void> {
        return if (babyInfoService.deleteBaby(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}