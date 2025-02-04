package com.mehrbod.di

import com.mehrbod.data.datasource.LocalTedTalkDataSource
import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.repository.TedTalkRepository
import com.mehrbod.domain.TedTalkService
import org.koin.dsl.module

val diModule = module {
    single<TedTalkDataSource> { LocalTedTalkDataSource() }
    single { TedTalkRepository(get()) }
    single { TedTalkService(get()) }
}