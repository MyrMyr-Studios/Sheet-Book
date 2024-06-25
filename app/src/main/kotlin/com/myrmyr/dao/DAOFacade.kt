package com.myrmyr.dao

import com.myrmyr.models.*

interface DAOFacade {
    // User
    suspend fun allUsers(): MutableList<User>
    suspend fun addNewUser(name: String, email: String, password: String): User?
    suspend fun editUser(userId: Int, name: String, email: String, password: String): Boolean
    suspend fun deleteUser(userId: Int): Boolean
    suspend fun findUserByEmail(email: String): User?
    suspend fun findUserById(userId: Int): User?

    // Sheet
    suspend fun allSheets(): MutableList<Sheet>
    suspend fun addNewSheet(sheet: Sheet): Sheet?
    suspend fun editSheet(sheet: Sheet): Boolean
    suspend fun deleteSheet(sheetId: Int): Boolean
    suspend fun findSheetById(sheetId: Int): Sheet?
    suspend fun findSheetByOwnerId(ownerId: Int): MutableList<Sheet>
    suspend fun findSheetByNameAndOwnerid(name: String, ownerId: Int): MutableList<Sheet>
    suspend fun findSheetByCampaign(campaignId: Int): MutableList<Sheet>

    // Campaign
    suspend fun allCampaigns(): MutableList<Campaign>
    suspend fun addNewCampaign(name: String): Campaign?
    suspend fun deleteCampaign(campaignId: Int): Boolean
    suspend fun findCampaignById(campaignId: Int): Campaign?
    suspend fun findCampaignByName(name: String): MutableList<Campaign>
    suspend fun addSheetToCampaign(sheetId: Int, campaignId: Int): Boolean
    suspend fun changeSheetCampaign(sheetId: Int, campaignId: Int): Boolean
    suspend fun getCampaignSheets(campaignId: Int): MutableList<Sheet>
    suspend fun addUserToCampaign(userId: Int, campaignId: Int): Boolean
    suspend fun removeUserFromCampaign(userId: Int, campaignId: Int): Boolean
    suspend fun getCampaignUsers(campaignId: Int): MutableList<Int>
    suspend fun getUserCampaigns(userId: Int): MutableList<Campaign>
}