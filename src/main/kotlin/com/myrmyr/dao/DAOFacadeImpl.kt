package com.myrmyr.dao

import com.myrmyr.dao.DatabaseSingleton.dbQuery
import com.myrmyr.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToUser(row: ResultRow) = User(
        userId = row[Users.userId],
        name = row[Users.name],
        email = row[Users.email],
        password = row[Users.password]
    )

    override suspend fun allUsers(): MutableList<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }.toMutableList()

    override suspend fun addNewUser(name: String, email: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun editUser(userId: Int, name: String, email: String, password: String): Boolean = dbQuery {
        Users.update({ Users.userId eq userId }) {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.password] = password
        } > 0
    }

    override suspend fun deleteUser(userId: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.userId eq userId } > 0
    }

    override suspend fun findUserByEmail(email: String): User? = dbQuery {
        Users
            .select { Users.email eq email }
            .map(::resultRowToUser)
            .singleOrNull()
    }
}

val dao: DAOFacade = DAOFacadeImpl()