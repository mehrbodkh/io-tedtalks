package data

import com.mehrbod.data.model.TedTalkDto

fun getSampleData() = listOf<TedTalkDto>(
        TedTalkDto(
            "sample_title",
            "sample_author",
            "11-11-2011",
            1000L,
            1000L,
            "sample_link",
        ),
        TedTalkDto(
            "sample_title_2",
            "sample_author_2",
            "12-11-2011",
            12000L,
            12000L,
            "sample_link_2",
        ),
        TedTalkDto(
            "sample_title_3",
            "sample_author_3",
            "13-11-2011",
            14000L,
            12000L,
            "sample_link_3",
        ),
    )