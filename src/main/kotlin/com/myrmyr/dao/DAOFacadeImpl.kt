package com.myrmyr.dao

import com.myrmyr.models.User

class DAOFacadeImpl : DAOFacade {
    override suspend fun addNewUser(name: String, email: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun editUser(userId: Int, name: String, email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findUserByEmail(email: String): User {
        TODO("Not yet implemented")
    }
}