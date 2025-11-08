package com.angelhack.babycaremall.backend.module.baby.repository

import com.angelhack.babycaremall.backend.module.baby.dto.BabyInfo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BabyInfoRepository : MongoRepository<BabyInfo, String>