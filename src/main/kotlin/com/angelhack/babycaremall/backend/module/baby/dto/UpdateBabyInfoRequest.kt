package com.angelhack.babycaremall.backend.module.baby.dto

data class UpdateBabyInfoRequest(
    val name: String,
    val gender: Gender,
    val birthDate: String,
    val months: Int,
    val profileImageUrl: String? = null
)