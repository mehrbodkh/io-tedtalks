package com.mehrbod.data.repository

import com.mehrbod.data.datasource.TedTalkDataSource
import com.mehrbod.data.model.convertToDomainModel
import com.mehrbod.domain.model.TedTalk
import com.mehrbod.domain.model.convertToDTO

class TedTalkRepository(
    private val localDataSource: TedTalkDataSource
) {

    suspend fun getTedTalks(): List<TedTalk> = localDataSource
        .fetchAll()
        .map { it.convertToDomainModel() }

    suspend fun saveTedTalks(tedTalks: List<TedTalk>) = localDataSource.save(
        tedTalks.map { it.convertToDTO() }
    )
}
