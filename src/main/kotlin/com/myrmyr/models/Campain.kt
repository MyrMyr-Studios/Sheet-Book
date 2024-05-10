package com.myrmyr.models

import kotlinx.serialization.Serializable

@Serializable
class Campain (
    val campainId: Int,
    val name: String,
    val userList: MutableList<User>,
    val sheetList: MutableList<Sheet>
) {}