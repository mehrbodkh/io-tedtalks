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
    fun `should add talk`() = testApplication {
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

    @Test
    fun `should remove talk`() = testApplication {
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
        client.post("/talks/remove") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(getSampleData().first())
        }
        val response = client.get("/talks")

        assertEquals(getSampleData().subList(1, 3), response.body())
    }

    @Test
    fun `should get most influential talk`() = testApplication {
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
        val response = client.get("/talks/top_talks")

        assertEquals(getSampleData().reversed(), response.body<List<TedTalk>>().distinct())
    }

    @Test
    fun `should get top talk per year`() = testApplication {
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
        val response = client.get("/talks/top_per_year")

        assertEquals(
            listOf(
                mapOf("2011" to getSampleData().first()),
                mapOf("2013" to getSampleData().last())
            ), response.body<List<Map<String, TedTalk>>>()
        )
    }

    fun getSampleData() = listOf<TedTalk>(
        TedTalk(
            "sample_title",
            "sample_author",
            "September 2011",
            1000L,
            1000L,
            "https://sample_link.com",
        ),
        TedTalk(
            "sample_title_2",
            "sample_author_2",
            "May 2013",
            12000L,
            12000L,
            "https://sample_link.com",
        ),
        TedTalk(
            "sample_title_3",
            "sample_author_3",
            "August 2013",
            14000L,
            12000L,
            "https://sample_link.com",
        ),
    )
}
