package com.myrmyr

import com.myrmyr.models.*
import com.myrmyr.plugins.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.*
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import com.myrmyr.dao.*
import org.junit.Before
import org.junit.After
import org.junit.Test
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

fun dbInit() {
    Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver"
    )
    transaction {
        SchemaUtils.create(Users)
        Users.deleteAll()
        SchemaUtils.create(Sheets)
        Sheets.deleteAll()
        SchemaUtils.create(Campaigns)
        Campaigns.deleteAll()
        SchemaUtils.create(RelationUserCampaign)
        RelationUserCampaign.deleteAll()
    }
}

val dao: DAOFacade = DAOFacadeImpl()

val testUser: User = User(
    name = "Ze testinho",
    email = "xXx_TestinhoReiDelas_xXx@email.xyz",
    password = "teste"
)

val testSheet: Sheet = Sheet(
    campaignId = null,
    name = "Sir Teste, o Assertivo",
    level = 0,
    class_t = "",
    background  = "",
    race = "",
    alignment = "",
    xp = 0,
    strength = 0,
    dexterity = 0,
    constitution = 0,
    intelligence = 0,
    wisdom = 0,
    charisma = 0,
    inspiration = "",
    proficiencyPoints = 0,
    armorClass = 0,
    initiative = 0,
    speed = 0,
    personalityTraits = "",
    ideals = "",
    bonds = "",
    flaws = "",
    features = "",
    traits = "",
    equipment = "",
    proficiencies = "",
    perception = "",
    languages = "",
    hp = 0,
    temporaryHp = 0,
    hitDice = "",
    deathSaves = "",
    attacks = "",
    spellcasting = "",
    skills = "",
    savingThrows = ""
)

class UserTest {

    @BeforeTest
    fun setup() {
        dbInit()
    }

    @Test
    fun addNewUserTest() {
        runBlocking {
            val resultUser: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(resultUser)
            testUser.userId = resultUser.userId
            assertEquals(resultUser, testUser)
        }
    }

    @Test
    fun findUserByEmailTest() {
        runBlocking {
            assertNull(dao.findUserByEmail(testUser.email))
            assertNotNull(dao.addNewUser(testUser.name, testUser.email, testUser.password))
            val resultUser: User? = dao.findUserByEmail(testUser.email)
            assertNotNull(resultUser)
            testUser.userId = resultUser.userId
            assertEquals(resultUser, testUser)
        }
    }

    @Test
    fun findUserByIdTest() {
        runBlocking {
            assertNull(dao.findUserById(2))
            assertNotNull(dao.addNewUser(testUser.name, testUser.email, testUser.password))
            val resultUser: User? = dao.findUserById(2)
            assertNotNull(resultUser)
            testUser.userId = resultUser.userId
            assertEquals(resultUser, testUser)
        }
    }
}

class SheetTest() {

    @BeforeTest
    fun setup() {
        dbInit()
    }

    @Test
    fun addNewSheetTest() {
        runBlocking {
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            testSheet.ownerId = user.userId
            val resultSheet: Sheet? = dao.addNewSheet(testSheet)
            assertNotNull(resultSheet)
        }
    }

    @Test
    fun findSheetByOwnerIdTest() {
        runBlocking {
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            testSheet.ownerId = user.userId
            assertEquals(dao.findSheetByOwnerId(user.userId).size, 0)
            val addedSheet: Sheet? = dao.addNewSheet(testSheet)
            assertNotNull(addedSheet)
            val searchedSheets: MutableList<Sheet> = dao.findSheetByOwnerId(user.userId)
            assertEquals(searchedSheets.size, 1)
            val searchedSheet: Sheet = searchedSheets[0]
            assertNotNull(searchedSheet)
            assertEquals(searchedSheet, addedSheet)
        }
    }

    @Test
    fun findSheetByIdTest() {
        runBlocking {
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            testSheet.ownerId = user.userId
            val addedSheet: Sheet? = dao.addNewSheet(testSheet)
            assertNotNull(addedSheet)
            val searchedSheet: Sheet? = dao.findSheetById(addedSheet.sheetId)
            assertNotNull(searchedSheet)
            assertEquals(searchedSheet, addedSheet)
        }
    }

    @Test
    fun editSheetTest() {
        runBlocking {
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            testSheet.ownerId = user.userId
            val addedSheet: Sheet? = dao.addNewSheet(testSheet)
            assertNotNull(addedSheet)
            val newSheet: Sheet = Sheet(
                campaignId = null,
                name = "Editadus, o Pressagio da Verdade",
                level = 0,
                class_t = "",
                background  = "",
                race = "",
                alignment = "",
                xp = 0,
                strength = 0,
                dexterity = 0,
                constitution = 0,
                intelligence = 0,
                wisdom = 0,
                charisma = 0,
                inspiration = "",
                proficiencyPoints = 0,
                armorClass = 0,
                initiative = 0,
                speed = 0,
                personalityTraits = "",
                ideals = "",
                bonds = "",
                flaws = "",
                features = "",
                traits = "",
                equipment = "",
                proficiencies = "",
                perception = "",
                languages = "",
                hp = 0,
                temporaryHp = 0,
                hitDice = "",
                deathSaves = "",
                attacks = "",
                spellcasting = "",
                skills = "",
                savingThrows = ""
            )
            newSheet.ownerId = addedSheet.ownerId
            newSheet.sheetId = addedSheet.sheetId
            assertTrue(dao.editSheet(newSheet))
            assertNotNull(dao.findSheetById(addedSheet.sheetId))
            assertEquals(newSheet, dao.findSheetById(addedSheet.sheetId))
        }
    }

    @Test
    fun deleteSheetTest() {
        runBlocking {
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            testSheet.ownerId = user.userId
            val addedSheet: Sheet? = dao.addNewSheet(testSheet)
            assertNotNull(addedSheet)
            assertTrue(dao.deleteSheet(addedSheet.sheetId))
            assertNull(dao.findSheetById(addedSheet.sheetId))
        }
    }
}

class CampaignTest() {

    @BeforeTest
    fun setup() {
        dbInit()
    }

    @Test
    fun addNewCampaignTest() {
        runBlocking {
            val campaignName: String = "Divine Testers"
            val campaign: Campaign? = dao.addNewCampaign(campaignName)
            assertNotNull(campaign)
            assertEquals(campaign.name, campaignName)
            assertTrue(campaign.userList.isEmpty())
            assertTrue(campaign.sheetList.isEmpty())
        }
    }

    @Test
    fun findCampaignByIdTest() {
        runBlocking {
            val campaignName: String = "Divine Testers"
            val campaign: Campaign? = dao.addNewCampaign(campaignName)
            assertNotNull(campaign)
            val ret: Campaign? = dao.findCampaignById(campaign.campaignId)
            assertNotNull(ret)
            assertEquals(ret, campaign)
        }
    }

    @Test
    fun addUserToCampaignTest() {
        runBlocking {
            val campaignName: String = "Divine Testers"
            val campaign: Campaign? = dao.addNewCampaign(campaignName)
            assertNotNull(campaign)
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            assertTrue(dao.addUserToCampaign(user.userId, campaign.campaignId))
            val ret: Campaign? = dao.findCampaignById(campaign.campaignId)
            assertNotNull(ret)
            assertContains(ret.userList, user.userId)
        }
    }

    @Test
    fun getUserCampaignsTest() {
        runBlocking {
            val campaign1: Campaign? = dao.addNewCampaign("Divine Testers")
            assertNotNull(campaign1)
            val campaign2: Campaign? = dao.addNewCampaign("Test is Unbreakable")
            assertNotNull(campaign2)
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            assertTrue(dao.addUserToCampaign(user.userId, campaign1.campaignId))
            assertTrue(dao.addUserToCampaign(user.userId, campaign2.campaignId))
            val campList: MutableList<Campaign> = dao.getUserCampaigns(user.userId)
            assertEquals(campList.size, 2)
            assertEquals(campList[0].campaignId, campaign1.campaignId)
            assertEquals(campList[1].campaignId, campaign2.campaignId)
        }
    }

    @Test
    fun removeUserFromCampaignTest() {
        runBlocking {
            val campaign: Campaign? = dao.addNewCampaign("Test is Unbreakable")
            assertNotNull(campaign)
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            assertTrue(dao.addUserToCampaign(user.userId, campaign.campaignId))
            var ret: Campaign? = dao.findCampaignById(campaign.campaignId)
            assertNotNull(ret)
            assertContains(ret.userList, user.userId)
            assertTrue(dao.removeUserFromCampaign(user.userId, campaign.campaignId))
            ret = dao.findCampaignById(campaign.campaignId)
            assertNotNull(ret)
            assertFalse(user.userId in ret.userList)
        }
    }

    @Test
    fun addSheetToCampaignTest() {
        runBlocking {
            val campaign: Campaign? = dao.addNewCampaign("Test Tendency")
            assertNotNull(campaign)
            val user: User? = dao.addNewUser(testUser.name, testUser.email, testUser.password)
            assertNotNull(user)
            testSheet.ownerId = user.userId
            val addedSheet: Sheet? = dao.addNewSheet(testSheet)
            assertNotNull(addedSheet)
            assertTrue(dao.addSheetToCampaign(addedSheet.sheetId, campaign.campaignId))
            val ret: Campaign? = dao.findCampaignById(campaign.campaignId)
            assertNotNull(ret)
            assertContains(ret.sheetList, addedSheet.sheetId)
        }
    }

    @Test
    fun deleteCampaignTest() {
        runBlocking {
            val campaign: Campaign? = dao.addNewCampaign("Test Tendency")
            assertNotNull(campaign)
            var ret: Campaign? = dao.findCampaignById(campaign.campaignId)
            assertNotNull(ret)
            assertTrue(dao.deleteCampaign(campaign.campaignId))
            ret = dao.findCampaignById(campaign.campaignId)
            assertNull(ret)
        }
    }
}