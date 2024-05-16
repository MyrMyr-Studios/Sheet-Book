package com.myrmyr.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

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
    val userId = reference("userId", Users.userId)
    val campaignId = reference("campaignId", Campaigns.campaignId)
}