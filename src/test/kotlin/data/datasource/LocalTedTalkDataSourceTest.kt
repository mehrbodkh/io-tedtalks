package com.mehrbod.data.datasource

import com.mehrbod.data.model.TedTalkDto
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LocalTedTalkDataSourceTest {

    @InjectMockKs
    lateinit var dataSource: LocalTedTalkDataSource

    @BeforeTest
    fun setup() {
        dataSource = LocalTedTalkDataSource()
    }

    @Test
    fun `should save data`() = runTest {
        val mockedData = listOf<TedTalkDto>(mockk(), mockk())
        dataSource.save(mockedData)

        assertEquals(mockedData, dataSource.fetchAll())
    }

    @Test
    fun `should save new data over old data`() = runTest {
        val oldMockedData = listOf<TedTalkDto>(mockk(), mockk())
        val newMockedData = listOf<TedTalkDto>(mockk(), mockk(), mockk())
        dataSource.save(oldMockedData)
        dataSource.save(newMockedData)

        assertEquals(newMockedData, dataSource.fetchAll())
    }
}
