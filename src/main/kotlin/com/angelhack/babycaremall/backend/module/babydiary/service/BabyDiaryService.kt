package com.angelhack.babycaremall.backend.module.babydiary.service

import com.angelhack.babycaremall.backend.module.babydiary.dto.BabyDiary
import com.angelhack.babycaremall.backend.module.babydiary.repository.BabyDiaryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BabyDiaryService(
    private val babyDiaryRepository: BabyDiaryRepository
) {
    
    fun getAllDiaries(): List<BabyDiary> {
        return babyDiaryRepository.findAll()
    }
    
    fun getDiaryById(id: String): Optional<BabyDiary> {
        return babyDiaryRepository.findById(id)
    }
    
    fun createDiary(diary: BabyDiary): BabyDiary {
        return babyDiaryRepository.save(diary)
    }
    
    fun updateDiary(id: String, updatedDiary: BabyDiary): Optional<BabyDiary> {
        return if (babyDiaryRepository.existsById(id)) {
            Optional.of(babyDiaryRepository.save(updatedDiary.copy(id = id)))
        } else {
            Optional.empty()
        }
    }
    
    fun deleteDiary(id: String): Boolean {
        return if (babyDiaryRepository.existsById(id)) {
            babyDiaryRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}