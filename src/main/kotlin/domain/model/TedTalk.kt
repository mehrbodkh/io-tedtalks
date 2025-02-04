package com.mehrbod.domain.model

import com.mehrbod.data.model.TedTalkDto

data class TedTalk(
    val title: String,
    val author: String?,
    val date: String,
    val views: Long?,
    val likes: Long,
    val link: String,
)

fun TedTalk.convertToDTO() = TedTalkDto(
    title,
    author,
    date,
    views,
    likes,
    link,
)