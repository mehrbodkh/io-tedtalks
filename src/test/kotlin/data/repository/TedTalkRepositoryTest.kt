package com.mehrbod.data.repository

import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.model.TedTalkDto
import com.mehrbod.data.model.convertToDomainModel
import com.mehrbod.domain.model.TedTalk
import com.mehrbod.domain.model.convertToDTO
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TedTalkRepositoryTest {

    @MockK
    lateinit var dataSource: TedTalkDataSource

    @InjectMockKs
    lateinit var repository: TedTalkRepository

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { dataSource.save(any()) } returns Unit
    }

    @Test
    fun `should save to data source`() = runTest {
        val mockedTedTalks = listOf<TedTalk>(mockk(relaxed = true), mockk(relaxed = true), mockk(relaxed = true))

        repository.saveTedTalks(mockedTedTalks)

        coVerify { dataSource.save(mockedTedTalks.map { it.convertToDTO() }) }
        mockedTedTalks.forEach { talk ->
            verify { talk.convertToDTO() }
        }
    }

    @Test
    fun `should retrieve data`() = runTest {
        val mockedTedTalksDto = listOf<TedTalkDto>(mockk(relaxed = true), mockk(relaxed = true), mockk(relaxed = true))
        coEvery { dataSource.fetchAll() } returns mockedTedTalksDto

        val result = repository.getTedTalks()

        assertEquals(mockedTedTalksDto.map { it.convertToDomainModel() }, result)
        mockedTedTalksDto.forEach { talk ->
            verify { talk.convertToDomainModel() }
        }
    }
}
