package com.mehrbod.data.model

import com.mehrbod.domain.model.TedTalk

data class TedTalkDto(
    val title: String,
    val author: String?,
    val date: String,
    val views: Int?,
    val likes: Int,
    val link: String,
)

fun TedTalkDto.convertToDomainModel() = TedTalk(
    title,
    author,
    date,
    views,
    likes,
    link,
)
