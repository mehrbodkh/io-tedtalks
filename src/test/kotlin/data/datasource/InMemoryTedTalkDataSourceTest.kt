package com.mehrbod.data.datasource

import data.getSampleData
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class InMemoryTedTalkDataSourceTest {

    @InjectMockKs
    lateinit var dataSource: InMemoryTedTalkDataSource

    @BeforeTest
    fun setup() {
        dataSource = InMemoryTedTalkDataSource()
    }

    @Test
    fun `should save data`() = runTest {
        val mockedData = getSampleData()
        dataSource.saveBatch(mockedData)

        assertEquals(mockedData, dataSource.fetchAll())
    }

    @Test
    fun `should add data`() = runTest {
        val mockedData = getSampleData().first()
        dataSource.add(mockedData)

        assertContains(dataSource.fetchAll(), mockedData)
    }

    @Test
    fun `should update data`() = runTest {
        dataSource.saveBatch(getSampleData())

        val mockedData = getSampleData().first().apply {
            title = "another_title"
        }
        dataSource.update(getSampleData().first(), mockedData)

        assertContains(dataSource.fetchAll(), mockedData)
    }

    @Test
    fun `should remove data`() = runTest {
        dataSource.saveBatch(getSampleData())

        dataSource.remove(getSampleData().first())

        assertFalse(dataSource.fetchAll().contains(getSampleData().first()))
    }
}
