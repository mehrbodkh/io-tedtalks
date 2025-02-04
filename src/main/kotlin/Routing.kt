package com.mehrbod

import com.mehrbod.domain.TedTalkService
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(Resources)
    val service by inject<TedTalkService>()
    routing {
        get("/") {
            call.respond(service.run())
        }
    }
}
