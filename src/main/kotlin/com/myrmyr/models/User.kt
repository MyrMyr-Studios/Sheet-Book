package com.myrmyr.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class User (
    var userId: Int = -1,
    val name: String,
    val email: String,
    val password: String
) {}

val userStorage = mutableListOf<User>()

object Users : Table() {
    val userId = integer("userId").autoIncrement()
    val name = varchar("name", 128)
    val email = varchar("email", 64)
    val password = varchar("password", 128)

    override val primaryKey = PrimaryKey(userId)
}