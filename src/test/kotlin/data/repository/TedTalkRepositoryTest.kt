package com.mehrbod.data.repository

import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.model.TedTalkDto
import com.mehrbod.data.model.convertToDomainModel
import com.mehrbod.domain.model.TedTalk
import com.mehrbod.domain.model.convertToDTO
import io.mockk.*
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TedTalkRepositoryTest {

    private val inMemoryDataSource: TedTalkDataSource = mockk(relaxed = true)
    private val persistentDataSource: TedTalkDataSource = mockk(relaxed = true)
    private lateinit var repository: TedTalkRepository

    private val sampleDtoList = listOf(
        TedTalkDto("title1", "author1", "January 2020", 1000L, 500L, "link1"),
        TedTalkDto("title2", "author2", "February 2021", 2000L, 1500L, "link2")
    )

    private val sampleDomainList = sampleDtoList.map { it.convertToDomainModel() }

    @BeforeTest
    fun setup() {
        repository = TedTalkRepository(inMemoryDataSource, persistentDataSource)
        clearMocks(inMemoryDataSource, persistentDataSource)
    }

    @Test
    fun `should fetch talks from in-memory source`() = runTest {
        coEvery { inMemoryDataSource.fetchAll() } returns sampleDtoList

        val result = repository.getTedTalks()

        assertEquals(sampleDomainList, result)
        coVerify(exactly = 1) { inMemoryDataSource.fetchAll() }
        coVerify(exactly = 0) { persistentDataSource.fetchAll() }
    }

    @Test
    fun `should fetch talks from persistent source when in-memory is empty`() = runTest {
        coEvery { persistentDataSource.fetchAll() } returns sampleDtoList

        val result = repository.getTedTalks()

        assertEquals(sampleDomainList, result)
        coVerify(exactly = 1) { inMemoryDataSource.fetchAll() }
        coVerify(exactly = 1) { persistentDataSource.fetchAll() }
        coVerify(exactly = 1) { inMemoryDataSource.saveBatch(sampleDtoList) }
    }

    @Test
    fun `should add talk to both data sources`() = runTest {
        val newTalk = TedTalk("title3", "author3", "March 2022", 3000L, 2500L, "link3")
        val newTalkDto = newTalk.convertToDTO()

        repository.add(newTalk)

        coVerify(exactly = 1) { persistentDataSource.add(newTalkDto) }
    }

    @Test
    fun `should remove talk from both data sources`() = runTest {
        val talkToRemove = sampleDomainList.first()
        val talkDtoToRemove = talkToRemove.convertToDTO()

        repository.remove(talkToRemove)

        coVerify(exactly = 1) { persistentDataSource.remove(talkDtoToRemove) }
    }
}
