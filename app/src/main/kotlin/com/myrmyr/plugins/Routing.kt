package com.myrmyr.plugins

import com.myrmyr.routes.campaignRouting
import com.myrmyr.routes.sheetRouting
import com.myrmyr.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import java.io.File


fun Application.configureRouting() {
    routing {
        userRouting()
        sheetRouting()
        campaignRouting()
    }
}