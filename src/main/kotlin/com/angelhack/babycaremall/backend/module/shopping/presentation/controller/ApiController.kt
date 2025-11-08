package com.angelhack.babycaremall.backend.module.shopping.presentation.controller

import com.angelhack.babycaremall.backend.module.shopping.domain.service.User
import com.angelhack.babycaremall.backend.module.shopping.domain.service.UserService
import com.angelhack.babycaremall.backend.module.shopping.infrastructure.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/v1")
@RestController
class ApiController (val userService: UserService, private val userRepository: UserRepository) {

    @GetMapping("/name")
    fun index(@RequestParam value: String): List<User> {
        return userService.getByName(value)
    }
}
