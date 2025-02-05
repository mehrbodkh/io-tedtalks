package com.mehrbod.data.repository

import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.model.convertToDomainModel
import com.mehrbod.domain.model.TedTalk
import com.mehrbod.domain.model.convertToDTO

class TedTalkRepository(
    private val inMemoryDataSource: TedTalkDataSource,
    private val persistentDataSource: TedTalkDataSource,
) {

    suspend fun getTedTalks(): List<TedTalk> = inMemoryDataSource.fetchAll()
        .ifEmpty {
            persistentDataSource.fetchAll().also {
                inMemoryDataSource.saveBatch(it)
            }
        }
        .map { it.convertToDomainModel() }

    suspend fun add(tedTalk: TedTalk) {
        persistentDataSource.add(tedTalk.convertToDTO())
        inMemoryDataSource.add(tedTalk.convertToDTO())
    }

    suspend fun remove(tedTalk: TedTalk) {
        persistentDataSource.remove(tedTalk.convertToDTO())
        inMemoryDataSource.remove(tedTalk.convertToDTO())
    }
}
