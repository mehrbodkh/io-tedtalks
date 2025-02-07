package com.mehrbod.domain

import com.mehrbod.data.repository.TedTalkRepository
import com.mehrbod.domain.model.TedTalk
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TedTalkServiceTest {

    private val repository: TedTalkRepository = mockk(relaxed = true)
    private val service = TedTalkService(repository, Dispatchers.Unconfined)

    private val sampleData = listOf(
        TedTalk("title1", "author1", "January 2020", 1000L, 500L, "link1"),
        TedTalk("title2", "author2", "February 2021", 2000L, 1500L, "link2"),
        TedTalk("title3", "author1", "March 2022", 3000L, 2500L, "link3")
    )

    @BeforeTest
    fun setup() {
        clearMocks(repository)
    }

    @Test
    fun `should get talks filtered by author`() = runTest {
        coEvery { repository.getTedTalks() } returns sampleData

        val result = service.getTedTalks("author1")

        assertEquals(2, result.size)
        assertTrue(result.all { it.author == "author1" })
    }

    @Test
    fun `should add a talk`() = runTest {
        val newTalk = TedTalk("title4", "author3", "April 2023", 4000L, 3500L, "link4")
        coEvery { repository.add(newTalk) } just Runs

        service.addTedTalk(newTalk)

        coVerify { repository.add(newTalk) }
    }

    @Test
    fun `should remove a talk`() = runTest {
        val talkToRemove = sampleData.first()
        coEvery { repository.remove(talkToRemove) } just Runs

        service.removeTedTalk(talkToRemove)

        coVerify { repository.remove(talkToRemove) }
    }

    @Test
    fun `should get top talks sorted by influence score`() = runTest {
        coEvery { repository.getTedTalks() } returns sampleData

        val result = service.getTopTalks(2)

        assertEquals(2, result.size)
        assertEquals(sampleData.sortedByDescending { it.likes * (it.views ?: 1) * 0.01 }.take(2), result)
    }

    @Test
    fun `should get top talk per year`() = runTest {
        coEvery { repository.getTedTalks() } returns sampleData

        val result = service.getTopTalksPerYear()

        val expected = mapOf(
            2020 to sampleData[0],
            2021 to sampleData[1],
            2022 to sampleData[2]
        )

        assertEquals(expected, result.associate { it.entries.first().toPair() })
    }
}
