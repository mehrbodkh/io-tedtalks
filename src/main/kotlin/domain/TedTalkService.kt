package com.mehrbod.domain

import com.mehrbod.data.repository.TedTalkRepository
import com.mehrbod.domain.model.TedTalk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.map
import org.jetbrains.kotlinx.dataframe.io.readCSV
import java.net.URL

class TedTalkService(
    private val repository: TedTalkRepository
) {
    init {
        GlobalScope.launch {
            val tedTalks = readDataFromFile("iO_Data.csv")
            repository.saveTedTalks(tedTalks)
            println(repository.getTedTalks())
        }
    }

    fun run() {

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