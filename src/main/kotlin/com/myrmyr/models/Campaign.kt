package com.myrmyr.models

import kotlinx.serialization.Serializable

@Serializable
data class Campaign (
    var campaignId: Int = -1,
    val name: String,
    val userList: MutableList<Int> = mutableListOf<Int>(), // Lista dos ID's dos usuarios na campanha
    val sheetList: MutableList<Int> = mutableListOf<Int>() // Lista dos ID's das fichas na campanha
) {}

val campaignStorage = mutableListOf<Campaign>()