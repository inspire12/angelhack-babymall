package com.angelhack.babycaremall.backend.module.baby.service

import com.angelhack.babycaremall.backend.module.baby.dto.BabyInfo
import com.angelhack.babycaremall.backend.module.baby.repository.BabyInfoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class BabyInfoService(
    private val babyInfoRepository: BabyInfoRepository
) {
    
    fun getAllBabies(): List<BabyInfo> {
        return babyInfoRepository.findAll()
    }
    
    fun getBabyById(id: String): Optional<BabyInfo> {
        return babyInfoRepository.findById(id)
    }
    
    fun createBaby(baby: BabyInfo): BabyInfo {
        val babyWithTimestamp = baby.copy(
            id = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        return babyInfoRepository.save(babyWithTimestamp)
    }
    
    fun updateBaby(id: String, updatedBaby: BabyInfo): Optional<BabyInfo> {
        return if (babyInfoRepository.existsById(id)) {
            val babyWithTimestamp = updatedBaby.copy(
                id = id,
                updatedAt = LocalDateTime.now()
            )
            Optional.of(babyInfoRepository.save(babyWithTimestamp))
        } else {
            Optional.empty()
        }
    }
    
    fun deleteBaby(id: String): Boolean {
        return if (babyInfoRepository.existsById(id)) {
            babyInfoRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}