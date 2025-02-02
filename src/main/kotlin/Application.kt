package com.mehrbod

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureFrameworks()
    configureSerialization()
    configureRouting()
}
