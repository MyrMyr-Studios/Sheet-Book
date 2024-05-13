package com.myrmyr.dao

import com.myrmyr.models.*

interface DAOFacade {
    suspend fun addNewUser(name: String, email: String, password: String): User?
    suspend fun editUser(userId: Int, name: String, email: String, password: String): Boolean
    suspend fun deleteUser(userId: Int): Boolean
    suspend fun findUserByEmail(email: String): User
}