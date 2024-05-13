package com.myrmyr.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    var userId: Int = -1,
    val name: String,
    val email: String,
    val password: String
) {}

val userStorage = mutableListOf<User>()