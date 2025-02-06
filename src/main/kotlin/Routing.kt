package com.mehrbod

import com.mehrbod.controller.tedTalkRouter
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.resources.*

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(Resources)
    tedTalkRouter()
}
