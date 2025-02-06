package com.mehrbod.di

import com.mehrbod.data.datasource.InMemoryTedTalkDataSource
import com.mehrbod.data.datasource.PersistentTedTalkDataSource
import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.repository.TedTalkRepository
import com.mehrbod.domain.TedTalkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.koin.core.qualifier.named
import org.koin.dsl.module

val diModule = module {
    single<DataFrame.Companion> { DataFrame.Companion }
    single<CoroutineDispatcher>(named("io_dispatcher")) { Dispatchers.IO }
    single<TedTalkDataSource>(named("in_memory")) { InMemoryTedTalkDataSource() }
    single<TedTalkDataSource>(named("persistent")) {
        PersistentTedTalkDataSource(
            get(),
            get(qualifier = named("io_dispatcher"))
        )
    }
    single { TedTalkRepository(get(qualifier = named("in_memory")), get(qualifier = named("persistent"))) }
    single { TedTalkService(get()) }
}