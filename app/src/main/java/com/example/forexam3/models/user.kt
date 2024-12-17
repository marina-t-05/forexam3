package com.example.forexam3.models

import kotlinx.serialization.Serializable

@Serializable
data class user(
    val id: String,
    val name: String,
    val phone: String,
    val birthday: String,
    val image: String?
)
