package com.mehrbod.controller

import com.mehrbod.domain.TedTalkService
import com.mehrbod.domain.model.TedTalk
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.tedTalkRouter() {
    val service: TedTalkService by inject()
    routing {
        route("/talks") {
            get {
                val authorName = call.queryParameters["name"]
                val result = service.getTedTalks(authorName)
                call.respond(result)
            }
            post("/add") {
                val input: TedTalk = call.receive()
                service.addTedTalk(input)
            }
            post("/remove") {
                val input: TedTalk = call.receive()
                service.removeTedTalk(input)
            }
            get("/top_talks") {
                val number = call.queryParameters["n"]?.toInt() ?: 10
                val result = service.getTopTalks(number)
                call.respond(result)
            }
            get("/top_per_year") {
                val result = service.getTopTalksPerYear()
                call.respond(result)
            }
        }
    }
}
