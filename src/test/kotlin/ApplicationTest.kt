package com.mehrbod

import com.mehrbod.domain.model.TedTalk
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals


class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        environment {
            config = ApplicationConfig("application-testing.yaml")
        }
        val client = createClient {
            this.install(ContentNegotiation) {
                json()
            }
        }
        getSampleData().forEach {
            client.post("/talks/add") {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(it)
            }
        }
        val response = client.get("/talks")

        assertEquals(getSampleData(), response.body())
    }

    fun getSampleData() = listOf<TedTalk>(
        TedTalk(
            "sample_title",
            "sample_author",
            "11-11-2011",
            1000L,
            1000L,
            "https://sample_link.com",
        ),
        TedTalk(
            "sample_title_2",
            "sample_author_2",
            "12-11-2011",
            12000L,
            12000L,
            "https://sample_link.com",
        ),
        TedTalk(
            "sample_title_3",
            "sample_author_3",
            "13-11-2011",
            14000L,
            12000L,
            "https://sample_link.com",
        ),
    )
}
