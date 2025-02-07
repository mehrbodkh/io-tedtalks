package com.mehrbod.data.datasource

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.mehrbod.data.model.TedTalkDto
import io.ktor.server.config.*
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PersistentTedTalkDataSourceTest {

    private lateinit var dataSource: PersistentTedTalkDataSource
    private val csvReader: CsvReader = mockk()
    private val csvWriter: CsvWriter = mockk(relaxed = true)
    private val config: ApplicationConfig = mockk()

    @Before
    fun setup() {
        every { config.propertyOrNull("filename")?.getString() } returns "test.csv"
        dataSource = PersistentTedTalkDataSource(csvReader = csvReader, csvWriter = csvWriter, config = config)
    }

    @Test
    fun `should save batch`() = runTest {
        val talks = listOf(TedTalkDto("title", "author", "date", 100L, 50L, "link"))
        dataSource.saveBatch(talks)
        verify { csvWriter.writeAll(any(), "test.csv", append = true) }
    }

    @Test
    fun `should add talk`() = runTest {
        val talk = TedTalkDto("title", "author", "date", 100L, 50L, "link")
        dataSource.add(talk)
        verify { csvWriter.writeAll(any(), "test.csv", append = true) }
    }

    @Test
    fun `should update talk`() = runTest {
        val oldTalk = TedTalkDto("title", "author", "date", 100L, 50L, "link")
        val newTalk = TedTalkDto("new_title", "new_author", "new_date", 200L, 100L, "new_link")
        mockkObject(dataSource)
        coEvery { dataSource.remove(oldTalk) } just Runs
        coEvery { dataSource.add(newTalk) } just Runs

        dataSource.update(oldTalk, newTalk)

        coEvery { dataSource.remove(oldTalk) }
        coEvery { dataSource.add(newTalk) }
    }
}
