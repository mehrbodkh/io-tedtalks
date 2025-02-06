package com.mehrbod.data.datasource

import data.getSampleData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.readCSV
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersistentTedTalkDataSourceTest {

    @MockK
    lateinit var dataFrame: DataFrame.Companion

    private lateinit var dataSource: PersistentTedTalkDataSource

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        dataSource = PersistentTedTalkDataSource(
            dataFrame,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `should save batch`() = runTest {
        mockkObject(AnyFrame::class)
        every { dataFrame.readCSV(any<String>()) } returns mockk()
        dataSource.saveBatch(getSampleData())
    }

}