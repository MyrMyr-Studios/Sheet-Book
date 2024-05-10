package com.myrmyr.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val id: Int,
    val name: String,
    val email: String,
    val password: String
) {}

val userStorage = mutableListOf<User>()