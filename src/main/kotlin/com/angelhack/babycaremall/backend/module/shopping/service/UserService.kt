package com.angelhack.babycaremall.backend.module.shopping.domain.service

import com.angelhack.babycaremall.backend.module.shopping.infrastructure.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repo: UserRepository) {
    fun create(name: String, age: Int) = repo.save(User(name = name, age = age))
    fun getByName(name: String) = repo.findByName(name)
}