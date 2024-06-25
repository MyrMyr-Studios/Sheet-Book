package com.myrmyr.dao

import com.myrmyr.dao.DatabaseSingleton.dbQuery
import com.myrmyr.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    // User
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

    override suspend fun findUserById(userId: Int): User? = dbQuery {
        Users
            .select { Users.userId eq userId }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    // Sheet
    private fun resultRowToSheet(row: ResultRow) = Sheet(
        ownerId = row[Sheets.ownerId],
        sheetId = row[Sheets.sheetId],
        campaignId = row[Sheets.campaignId],
        name = row[Sheets.name],
        level = row[Sheets.level],
        class_t = row[Sheets.class_t],
        background = row[Sheets.background],
        race = row[Sheets.race],
        alignment = row[Sheets.alignment],
        xp = row[Sheets.xp],
        strength = row[Sheets.strength],
        dexterity = row[Sheets.dexterity],
        constitution = row[Sheets.constitution],
        intelligence = row[Sheets.intelligence],
        wisdom = row[Sheets.wisdom],
        charisma = row[Sheets.charisma],
        inspiration = row[Sheets.inspiration],
        proficiencyPoints = row[Sheets.proficiencyPoints],
        armorClass = row[Sheets.armorClass],
        initiative = row[Sheets.initiative],
        speed = row[Sheets.speed],
        personalityTraits = row[Sheets.personalityTraits],
        ideals = row[Sheets.ideals],
        bonds = row[Sheets.bonds],
        flaws = row[Sheets.flaws],
        features = row[Sheets.features],
        traits = row[Sheets.traits],
        equipment = row[Sheets.equipment],
        proficiencies = row[Sheets.proficiencies],
        perception = row[Sheets.perception],
        languages = row[Sheets.languages],
        hp = row[Sheets.hp],
        temporaryHp = row[Sheets.temporaryHp],
        hitDice = row[Sheets.hitDice],
        deathSaves = row[Sheets.deathSaves],
        attacks = row[Sheets.attacks],
        spellcasting = row[Sheets.spellcasting],
        skills = row[Sheets.skills],
        savingThrows = row[Sheets.savingThrows]
    )

    override suspend fun allSheets(): MutableList<Sheet> = dbQuery {
        Sheets.selectAll().map(::resultRowToSheet)
    }.toMutableList()

    override suspend fun addNewSheet(sheet: Sheet): Sheet? = dbQuery {
        val insertStatement = Sheets.insert {
            it[this.ownerId] = sheet.ownerId
            it[campaignId] = sheet.campaignId
            it[name] = sheet.name
            it[level] = sheet.level
            it[class_t] = sheet.class_t
            it[background] = sheet.background
            it[race] = sheet.race
            it[alignment] = sheet.alignment
            it[xp] = sheet.xp
            it[strength] = sheet.strength
            it[dexterity] = sheet.dexterity
            it[constitution] = sheet.constitution
            it[intelligence] = sheet.intelligence
            it[wisdom] = sheet.wisdom
            it[charisma] = sheet.charisma
            it[inspiration] = sheet.inspiration
            it[proficiencyPoints] = sheet.proficiencyPoints
            it[armorClass] = sheet.armorClass
            it[initiative] = sheet.initiative
            it[speed] = sheet.speed
            it[personalityTraits] = sheet.personalityTraits
            it[ideals] = sheet.ideals
            it[bonds] = sheet.bonds
            it[flaws] = sheet.flaws
            it[features] = sheet.features
            it[traits] = sheet.traits
            it[equipment] = sheet.equipment
            it[proficiencies] = sheet.proficiencies
            it[perception] = sheet.perception
            it[languages] = sheet.languages
            it[hp] = sheet.hp
            it[temporaryHp] = sheet.temporaryHp
            it[hitDice] = sheet.hitDice
            it[deathSaves] = sheet.deathSaves
            it[attacks] = sheet.attacks
            it[spellcasting] = sheet.spellcasting
            it[skills] = sheet.skills
            it[savingThrows] = sheet.savingThrows
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToSheet)
    }

    override suspend fun addSheetToCampaign(sheetId: Int, campaignId: Int): Boolean = dbQuery {
        Sheets.update({ Sheets.sheetId eq sheetId }) {
            it[Sheets.campaignId] = campaignId
        } > 0
    }

    override suspend fun editSheet(sheet: Sheet): Boolean = dbQuery {
        Sheets.update({Sheets.sheetId eq sheet.sheetId}) {
            it[campaignId] = sheet.campaignId
            it[name] = sheet.name
            it[level] = sheet.level
            it[class_t] = sheet.class_t
            it[background] = sheet.background
            it[race] = sheet.race
            it[alignment] = sheet.alignment
            it[xp] = sheet.xp
            it[strength] = sheet.strength
            it[dexterity] = sheet.dexterity
            it[constitution] = sheet.constitution
            it[intelligence] = sheet.intelligence
            it[wisdom] = sheet.wisdom
            it[charisma] = sheet.charisma
            it[inspiration] = sheet.inspiration
            it[proficiencyPoints] = sheet.proficiencyPoints
            it[armorClass] = sheet.armorClass
            it[initiative] = sheet.initiative
            it[speed] = sheet.speed
            it[personalityTraits] = sheet.personalityTraits
            it[ideals] = sheet.ideals
            it[bonds] = sheet.bonds
            it[flaws] = sheet.flaws
            it[features] = sheet.features
            it[traits] = sheet.traits
            it[equipment] = sheet.equipment
            it[proficiencies] = sheet.proficiencies
            it[perception] = sheet.perception
            it[languages] = sheet.languages
            it[hp] = sheet.hp
            it[temporaryHp] = sheet.temporaryHp
            it[hitDice] = sheet.hitDice
            it[deathSaves] = sheet.deathSaves
            it[attacks] = sheet.attacks
            it[spellcasting] = sheet.spellcasting
            it[skills] = sheet.skills
            it[savingThrows] = sheet.savingThrows
        } > 0
    }

    override suspend fun deleteSheet(sheetId: Int): Boolean = dbQuery {
        Sheets.deleteWhere { Sheets.sheetId eq sheetId } > 0
    }

    override suspend fun findSheetById(sheetId: Int): Sheet? = dbQuery {
        Sheets
            .select { Sheets.sheetId eq sheetId }
            .map(::resultRowToSheet)
            .singleOrNull()
    }

    override suspend fun findSheetByOwnerId(ownerId: Int): MutableList<Sheet> = dbQuery {
        Sheets
            .select { Sheets.ownerId eq ownerId }
            .map(::resultRowToSheet)
    }.toMutableList()

    override suspend fun findSheetByNameAndOwnerid(name: String, ownerId: Int): MutableList<Sheet> = dbQuery {
        Sheets
            .select { Sheets.ownerId eq ownerId }
            .andWhere { Sheets.name eq name }
            .map(::resultRowToSheet)
    }.toMutableList()

    override suspend fun findSheetByCampaign(campaignId: Int): MutableList<Sheet> = dbQuery {
        Sheets
            .select { Sheets.campaignId eq campaignId }
            .map(::resultRowToSheet)
    }.toMutableList()

    // Campaigns
    private fun resultRowToCampaign(row: ResultRow) = Campaign(
        campaignId = row[Campaigns.campaignId],
        name = row[Campaigns.name],
        userList = RelationUserCampaign
            .select { RelationUserCampaign.campaignId eq row[Campaigns.campaignId] }
            .map { r: ResultRow -> r[RelationUserCampaign.userId] }
            .toMutableList(),
        sheetList = Sheets
            .select { Sheets.campaignId eq row[Campaigns.campaignId] }
            .map { r: ResultRow -> r[Sheets.sheetId] }
            .toMutableList()
    )

    override suspend fun allCampaigns(): MutableList<Campaign> = dbQuery {
        Campaigns.selectAll().map(::resultRowToCampaign)
    }.toMutableList()

    override suspend fun addNewCampaign(name: String): Campaign? = dbQuery {
        val insertStatement = Campaigns.insert {
            it[Campaigns.name] = name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCampaign)
    }

    override suspend fun deleteCampaign(campaignId: Int): Boolean = dbQuery {
        (Campaigns.deleteWhere { Campaigns.campaignId eq campaignId } > 0) and (RelationUserCampaign.deleteWhere { RelationUserCampaign.campaignId eq campaignId } > 0)
    }

    override suspend fun findCampaignById(campaignId: Int): Campaign? = dbQuery {
        Campaigns
            .select { Campaigns.campaignId eq campaignId }
            .map(::resultRowToCampaign)
            .singleOrNull()
    }

    override suspend fun findCampaignByName(name: String): MutableList<Campaign> = dbQuery {
        Campaigns
            .select { Campaigns.name eq name }
            .map(::resultRowToCampaign)
            .toMutableList()
    }

    override suspend fun changeSheetCampaign(sheetId: Int, campaignId: Int): Boolean = dbQuery {
        Sheets.update({ Sheets.sheetId eq sheetId }) {
            it[Sheets.campaignId] = campaignId
        } > 0
    }

    override suspend fun getCampaignSheets(campaignId: Int): MutableList<Sheet> = dbQuery {
        Sheets
            .select { Sheets.campaignId eq campaignId }
            .map(::resultRowToSheet)
            .toMutableList()
    }

    override suspend fun addUserToCampaign(userId: Int, campaignId: Int): Boolean = dbQuery {
        val insertStatement = RelationUserCampaign.insert {
            it[RelationUserCampaign.userId] = userId
            it[RelationUserCampaign.campaignId] = campaignId
        }
        insertStatement.resultedValues?.singleOrNull()?.let { true } ?: false
    }

    override suspend fun removeUserFromCampaign(userId: Int, campaignId: Int): Boolean = dbQuery {
        RelationUserCampaign
            .deleteWhere { (RelationUserCampaign.campaignId eq campaignId) and (RelationUserCampaign.userId eq userId) } > 0
    }

    override suspend fun getCampaignUsers(campaignId: Int): MutableList<Int> = dbQuery {
        RelationUserCampaign
            .select { RelationUserCampaign.campaignId eq campaignId }
            .map { resultRow: ResultRow -> resultRow[RelationUserCampaign.userId] }
            .toMutableList()
    }

    override suspend fun getUserCampaigns(userId: Int): MutableList<Campaign> = dbQuery {
        RelationUserCampaign
            .select { RelationUserCampaign.userId eq userId }
            .map { resultRow: ResultRow -> resultRow[RelationUserCampaign.campaignId] }
            .map { campaignId: Int -> Campaigns.select { Campaigns.campaignId eq campaignId }.map(::resultRowToCampaign).single() }
            .toMutableList()
    }
}

val dao: DAOFacade = DAOFacadeImpl()