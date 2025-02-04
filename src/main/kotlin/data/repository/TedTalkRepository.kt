package com.mehrbod.data.repository

import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.model.convertToDomainModel
import com.mehrbod.domain.model.TedTalk

class TedTalkRepository(
    private val inMemoryDataSource: TedTalkDataSource,
    private val persistentDataSource: TedTalkDataSource,
) {

    suspend fun getTedTalks(): List<TedTalk> = inMemoryDataSource.fetchAll()
        .ifEmpty {
            persistentDataSource.fetchAll().also {
                inMemoryDataSource.save(it)
            }
        }
        .map { it.convertToDomainModel() }
}
