package com.myrmyr.dao

import com.myrmyr.models.*

interface DAOFacade {
    suspend fun allUsers(): MutableList<User>
    suspend fun addNewUser(name: String, email: String, password: String): User?
    suspend fun editUser(userId: Int, name: String, email: String, password: String): Boolean
    suspend fun deleteUser(userId: Int): Boolean // ! ! ! I N C O M P L E T A ! ! !
    suspend fun findUserByEmail(email: String): User?

    suspend fun allSheets(): MutableList<Sheet>
    suspend fun addNewSheet(sheet: Sheet): Sheet?
    suspend fun addSheetToCampaign(sheetId: Int, campaignId: Int): Boolean
    suspend fun editSheet(sheet: Sheet): Boolean
    suspend fun deleteSheet(sheetId: Int): Boolean // ! ! ! I N C O M P L E T A ! ! !
    suspend fun findSheetById(sheetId: Int): Sheet?
    suspend fun findSheetByNameAndOwnerid(name: String, ownerId: Int): MutableList<Sheet>
    suspend fun findSheetByCampaign(campaignId: Int): MutableList<Sheet>
}