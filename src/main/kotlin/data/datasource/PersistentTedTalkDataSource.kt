package com.mehrbod.data.datasource

import com.mehrbod.data.model.TedTalkDto
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.map
import org.jetbrains.kotlinx.dataframe.io.readCSV
import java.net.URL

class PersistentTedTalkDataSource(
    private val dataFrame: DataFrame.Companion = DataFrame.Companion
) : TedTalkDataSource {

    companion object {
        private const val FILE_NAME = "iO_Data.csv"
    }

    override suspend fun save(tedTalks: List<TedTalkDto>) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAll(): List<TedTalkDto> = dataFrame
        .readCSV(FILE_NAME)
        .map {
            TedTalkDto(
                it["title"] as String,
                it["author"] as String?,
                it["date"] as String,
                (it["views"] as String?)?.toLongOrNull(),
                it["likes"] as Long,
                (it["link"] as URL).toString()
            )
        }
}