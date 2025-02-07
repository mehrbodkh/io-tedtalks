package com.mehrbod.di

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.mehrbod.data.datasource.InMemoryTedTalkDataSource
import com.mehrbod.data.datasource.PersistentTedTalkDataSource
import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.repository.TedTalkRepository
import com.mehrbod.domain.TedTalkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val diModule = module {
    single { csvReader() }
    single { csvWriter() }
    single<CoroutineDispatcher>(named("io_dispatcher")) { Dispatchers.IO }
    single<CoroutineDispatcher>(named("default_dispatcher")) { Dispatchers.Default }
    single<TedTalkDataSource>(named("in_memory")) { InMemoryTedTalkDataSource() }
    single<TedTalkDataSource>(named("persistent")) {
        PersistentTedTalkDataSource(
            get(qualifier = named("io_dispatcher")),
            get(),
            get(),
            get()
        )
    }
    single { TedTalkRepository(get(qualifier = named("in_memory")), get(qualifier = named("persistent"))) }
    single { TedTalkService(get(), get(qualifier = named("default_dispatcher"))) }
}