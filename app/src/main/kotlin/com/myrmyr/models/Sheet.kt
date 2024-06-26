package com.myrmyr.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import com.myrmyr.models.*

@Serializable
data class Sheet (
    var ownerId: Int = -1,
    var sheetId: Int = -1,
    var campaignId: Int? = -1,
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

object Sheets: Table() {
    val ownerId = reference("ownerId", Users.userId, onDelete = ReferenceOption.CASCADE)
    val sheetId = integer("sheetId").autoIncrement()
    val campaignId = reference("campaignId", Campaigns.campaignId, onDelete = ReferenceOption.CASCADE).nullable()
    val name = varchar("name", 64)
    val level = integer("level")
    val class_t = varchar("class", 48)
    val background = varchar("background", 128)
    val race = varchar("race", 48)
    val alignment = varchar("alignment", 48)
    val xp = integer("xp")
    val strength = integer("strength")
    val dexterity = integer("dexterity")
    val constitution = integer("constitution")
    val intelligence = integer("intelligence")
    val wisdom = integer("wisdom")
    val charisma = integer("charisma")
    val inspiration = varchar("inspiration", 48)
    val proficiencyPoints = integer("proficiencyPoints")
    val armorClass = integer("armorClass")
    val initiative = integer("initiative")
    val speed = integer("speed")
    val personalityTraits = varchar("personalityTraits", 256)
    val ideals = varchar("ideals", 256)
    val bonds = varchar("bonds", 256)
    val flaws = varchar("flaws", 256)
    val features = varchar("features", 256)
    val traits = varchar("traits", 256)
    val equipment = varchar("equipment", 512)
    val proficiencies = varchar("proficiencies", 256)
    val perception = varchar("perception", 64)
    val languages = varchar("languages", 256)
    val hp = integer("hp")
    val temporaryHp = integer("temporaryHp")
    val hitDice = varchar("hitDice", 48)
    val deathSaves = varchar("deathSaves", 48)
    val attacks = varchar("attacks", 1024)
    val spellcasting = varchar("spellcasting", 1024)
    val skills = varchar("skills", 1024)
    val savingThrows = varchar("savingThrows", 64)

    override val primaryKey = PrimaryKey(sheetId)
}