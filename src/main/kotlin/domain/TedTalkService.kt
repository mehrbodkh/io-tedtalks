package com.mehrbod.domain

import com.mehrbod.data.repository.TedTalkRepository
import com.mehrbod.domain.model.TedTalk
import kotlinx.coroutines.CoroutineDispatcher
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class TedTalkService(
    private val repository: TedTalkRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun getTedTalks(authorsName: String?) = repository.getTedTalks()
        .filter { authorsName == null || it.author?.contains(authorsName, true) == true }

    suspend fun addTedTalk(tedTalk: TedTalk) {
        repository.add(tedTalk)
    }

    suspend fun removeTedTalk(tedTalk: TedTalk) {
        repository.remove(tedTalk)
    }

    suspend fun getTopTalks(number: Int) = with(defaultDispatcher) {
        repository
            .getTedTalks()
            .sortedByDescending { it.calculateInfluenceScore() }
            .take(number)
    }

    suspend fun getTopTalksPerYear() = with(defaultDispatcher) {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)

        repository
            .getTedTalks()
            .groupBy {
                try {
                    YearMonth.parse(it.date, formatter).year
                } catch (e: Exception) {
                    -1
                }
            }
            .filter { it.key >= 0 }
            .map { mapOf(it.key to it.value.maxBy { talks -> talks.calculateInfluenceScore() }) }
    }


    private fun TedTalk.calculateInfluenceScore() = (this.likes * (this.views ?: 1)) * 0.01
}