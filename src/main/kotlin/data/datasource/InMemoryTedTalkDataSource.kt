package com.mehrbod.data.datasource

import co.touchlab.stately.collections.ConcurrentMutableList
import com.mehrbod.data.model.TedTalkDto

class InMemoryTedTalkDataSource : TedTalkDataSource {
    private val tedTalks: MutableList<TedTalkDto> = ConcurrentMutableList()

    override suspend fun fetchAll(): List<TedTalkDto> {
        return tedTalks
    }

    override suspend fun saveBatch(tedTalks: List<TedTalkDto>) {
        this.tedTalks.addAll(tedTalks)
    }

    override suspend fun add(tedTalkDto: TedTalkDto) {
        this.tedTalks.add(tedTalkDto)
    }

    override suspend fun remove(tedTalkDto: TedTalkDto) {
        if (tedTalks.contains(tedTalkDto)) {
            this.tedTalks.remove(tedTalkDto)
        } else {
            throw RuntimeException("Talk doesn't exist")
        }
    }

    override suspend fun update(oldTedTalkDto: TedTalkDto, newTedTalkDto: TedTalkDto) {
        this.tedTalks
            .find { it == oldTedTalkDto }
            ?.apply {
                author = newTedTalkDto.author
                title = newTedTalkDto.title
                link = newTedTalkDto.link
                views = newTedTalkDto.views
                likes = newTedTalkDto.likes
                date = newTedTalkDto.date
            }
    }
}
