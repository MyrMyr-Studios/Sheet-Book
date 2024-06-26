package com.myrmyr.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import com.myrmyr.models.*

@Serializable
data class Campaign (
    var campaignId: Int = -1,
    val name: String,
    val userList: MutableList<Int> = mutableListOf<Int>(), // Lista dos ID's dos usuarios na campanha
    val sheetList: MutableList<Int> = mutableListOf<Int>() // Lista dos ID's das fichas na campanha
) {}

val campaignStorage = mutableListOf<Campaign>()

object Campaigns : Table() {
    val campaignId = integer("campaignId").autoIncrement()
    val name = varchar("name", 64)

    override val primaryKey = PrimaryKey(campaignId)
}

object RelationUserCampaign : Table() {
    val userId = integer("userId").references(Users.userId, onDelete = ReferenceOption.CASCADE)//reference("userId", Users.userId, OnDelete = ReferenceOptions.CASCADE)
    val campaignId = integer("campaignId").references(Campaigns.campaignId, onDelete = ReferenceOption.CASCADE)//reference("campaignId", Campaigns.campaignId)
}