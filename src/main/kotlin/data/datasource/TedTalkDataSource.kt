package com.mehrbod.data.datasource

import com.mehrbod.data.model.TedTalkDto

interface TedTalkDataSource {
    suspend fun saveBatch(tedTalks: List<TedTalkDto>)
    suspend fun fetchAll(): List<TedTalkDto>
    suspend fun add(tedTalkDto: TedTalkDto)
    suspend fun remove(tedTalkDto: TedTalkDto)
    suspend fun update(oldTedTalkDto: TedTalkDto, newTedTalkDto: TedTalkDto)
}