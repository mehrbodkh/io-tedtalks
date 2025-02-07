package com.mehrbod.data.datasource

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.mehrbod.data.model.TedTalkDto
import io.ktor.server.config.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File

class PersistentTedTalkDataSource(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val csvReader: CsvReader,
    private val csvWriter: CsvWriter,
    config: ApplicationConfig,
) : TedTalkDataSource {
    val mutex = Mutex()
    private val fileName: String = config.propertyOrNull("filename")?.getString() ?: "iO_Data.csv"

    override suspend fun saveBatch(tedTalks: List<TedTalkDto>) = withContext(ioDispatcher) {
        mutex.withLock {
            csvWriter.writeAll(
                tedTalks.map {
                    listOf(
                        it.title,
                        it.author,
                        it.date,
                        it.views.toString(),
                        it.likes.toString(),
                        it.link
                    )
                },
                fileName,
                append = true
            )
        }
    }

    override suspend fun fetchAll(): List<TedTalkDto> = withContext(ioDispatcher) {
        csvReader.openAsync(fileName) {
            readAllWithHeaderAsSequence().toList()
                .map { data ->
                    TedTalkDto(
                        data["title"]!!,
                        data["author"],
                        data["date"]!!,
                        data["views"]?.toLongOrNull(),
                        data["likes"]!!.toLong(),
                        data["link"]!!
                    )
                }
        }
    }

    override suspend fun add(tedTalkDto: TedTalkDto) {
        mutex.withLock {
            csvWriter.writeAll(
                listOf(
                    listOf(
                        tedTalkDto.title,
                        tedTalkDto.author,
                        tedTalkDto.date,
                        tedTalkDto.views.toString(),
                        tedTalkDto.likes.toString(),
                        tedTalkDto.link
                    )
                ), fileName, append = true
            )
        }
    }

    override suspend fun remove(tedTalkDto: TedTalkDto) {
        mutex.withLock {
            val currentValues = csvReader().readAll(File(fileName))
            csvWriter.writeAll(
                currentValues
                    .filterNot {
                        it[0] == tedTalkDto.title && it[1] == tedTalkDto.author
                    },
                fileName,
                append = false
            )
        }
    }

    override suspend fun update(oldTedTalkDto: TedTalkDto, newTedTalkDto: TedTalkDto) {
        remove(oldTedTalkDto)
        add(newTedTalkDto)
    }
}
