package com.mehrbod.data.datasource

import com.mehrbod.data.model.TedTalkDto

class LocalTedTalkDataSource : TedTalkDataSource {
    private val tedTalks: MutableList<TedTalkDto> = mutableListOf()

    override suspend fun fetchAll(): List<TedTalkDto> {
        return tedTalks
    }

    override suspend fun save(tedTalks: List<TedTalkDto>) {
        this.tedTalks.clear()
        this.tedTalks.addAll(tedTalks)
    }
}
