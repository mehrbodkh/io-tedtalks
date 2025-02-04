package com.mehrbod.data.datasource

import com.mehrbod.data.model.TedTalkDto

interface TedTalkDataSource {
    suspend fun save(tedTalks: List<TedTalkDto>)
    suspend fun fetchAll(): List<TedTalkDto>
}