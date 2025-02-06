package com.mehrbod.data.datasource

import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersistentTedTalkDataSourceTest {


    private lateinit var dataSource: PersistentTedTalkDataSource

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        dataSource = PersistentTedTalkDataSource(
            UnconfinedTestDispatcher(),
            mockk()
        )
    }

    @Test
    fun `should save batch`() = runTest {
    }

}