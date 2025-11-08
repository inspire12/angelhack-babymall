package com.angelhack.babycaremall.backend.module.shopping.infrastructure.repository

import com.angelhack.babycaremall.backend.module.shopping.domain.service.User
import org.springframework.data.mongodb.repository.MongoRepository


interface UserRepository : MongoRepository<User, String> {
    fun findByName(name: String): List<User>
}