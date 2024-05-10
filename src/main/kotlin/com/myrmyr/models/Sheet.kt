package com.myrmyr.models

import kotlinx.serialization.Serializable

@Serializable
data class Sheet (
    val ownerId: Int,
    var sheetId: Int = -1,
    val name: String,
    val level: Int,
    val class_t: String,
    val background: String,
    val race: String,
    val alignment: String,
    val xp: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val inspiration: String,
    val proficiencyPoints: Int,
    val armorClass: Int,
    val initiative: Int,
    val speed: Int,
    val personalityTraits: String,
    val ideals: String,
    val bonds: String,
    val flaws: String,
    val features: String,
    val traits: String,
    val equipment: String,
    val proficiencies: String,
    val perception: String,
    val languages: String,
    val hp: Int,
    val temporaryHp: Int,
    val hitDice: String,
    val deathSaves: String,
    val attacks: String,
    val spellcasting: String,
    val skills: String,
    val savingThrows: String
) {}

val sheetStorage = mutableListOf<Sheet>()