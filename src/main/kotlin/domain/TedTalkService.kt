package com.mehrbod.domain

import com.mehrbod.data.repository.TedTalkRepository
import com.mehrbod.domain.model.TedTalk
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.map
import org.jetbrains.kotlinx.dataframe.io.readCSV
import java.net.URL

class TedTalkService(
    private val repository: TedTalkRepository
) {
    suspend fun getTedTalks(authorsName: String?) = repository.getTedTalks()
        .filter { authorsName == null || it.author?.contains(authorsName, true) == true }

    suspend fun addTedTalk(tedTalk: TedTalk) {
        repository.add(tedTalk)
    }

    suspend fun removeTedTalk(tedTalk: TedTalk) {
        repository.remove(tedTalk)
    }

    private fun readDataFromFile(fileName: String) = DataFrame.readCSV(fileName).map {
        TedTalk(
            it["title"] as String,
            it["author"] as String?,
            it["date"] as String,
            (it["views"] as String?)?.toLongOrNull(),
            it["likes"] as Long,
            (it["link"] as URL).toString()
        )
    }
}