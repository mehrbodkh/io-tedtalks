package com.mehrbod.data.datasource

import com.mehrbod.data.model.TedTalkDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.append
import org.jetbrains.kotlinx.dataframe.api.drop
import org.jetbrains.kotlinx.dataframe.api.map
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.io.readCSV
import org.jetbrains.kotlinx.dataframe.io.writeCSV
import java.net.URL

class PersistentTedTalkDataSource(
    private val dataFrame: DataFrame.Companion = DataFrame.Companion,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TedTalkDataSource {

    companion object {
        private const val FILE_NAME = "iO_Data.csv"
    }

    override suspend fun saveBatch(tedTalks: List<TedTalkDto>) = withContext(ioDispatcher) {
        tedTalks
            .toDataFrame()
            .writeCSV(FILE_NAME)
    }

    override suspend fun fetchAll(): List<TedTalkDto> = withContext(ioDispatcher) {
        dataFrame
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

    override suspend fun add(tedTalkDto: TedTalkDto) {
        dataFrame
            .readCSV(FILE_NAME)
            .append(
                tedTalkDto.title,
                tedTalkDto.author,
                tedTalkDto.date,
                tedTalkDto.views?.toString(),
                tedTalkDto.likes,
                URL(tedTalkDto.link),
            )
            .writeCSV(FILE_NAME)
    }

    override suspend fun remove(tedTalkDto: TedTalkDto) {
        dataFrame
            .readCSV(FILE_NAME)
            .drop { it["title"] == tedTalkDto.title && it["author"] == tedTalkDto.author}
            .writeCSV(FILE_NAME)
    }

    override suspend fun update(oldTedTalkDto: TedTalkDto, newTedTalkDto: TedTalkDto) {
        remove(oldTedTalkDto)
        add(newTedTalkDto)
    }
}
