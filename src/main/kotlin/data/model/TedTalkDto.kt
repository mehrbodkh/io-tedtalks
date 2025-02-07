package com.mehrbod.data.model

import com.mehrbod.domain.model.TedTalk

data class TedTalkDto(
    var title: String,
    var author: String?,
    var date: String,
    var views: Long?,
    var likes: Long,
    var link: String,
)

fun TedTalkDto.convertToDomainModel() = TedTalk(
    title,
    author,
    date,
    views,
    likes,
    link,
)
