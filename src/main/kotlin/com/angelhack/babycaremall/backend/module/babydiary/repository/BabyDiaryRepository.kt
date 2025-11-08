package com.angelhack.babycaremall.backend.module.babydiary.repository

import com.angelhack.babycaremall.backend.module.babydiary.dto.BabyDiary
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BabyDiaryRepository : MongoRepository<BabyDiary, Long>