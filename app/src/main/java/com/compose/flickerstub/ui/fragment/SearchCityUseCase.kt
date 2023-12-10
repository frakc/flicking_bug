package com.compose.flickerstub.ui.fragment

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(

) {
    suspend fun invoke(query: String): Flow<SearchCity> {
        val recents = cities.take(5)
        val flow = callbackFlow<SearchCity> {
            trySend(SearchCity(recent = recents))
            delay(300L)
            val c = if (query.isEmpty()) {
                cities
            } else {
                cities.filter { it.lowercase().contains(query.lowercase()) }
            }
            trySend(
                SearchCity(
                    recent = recents,
                    fromServer = c)
            )

            awaitClose()
        }
        return flow
    }

    val cities = listOf(
        "New York",
        "Los Angeles",
        "Chicago",
        "Houston",
        "Phoenix",
        "Philadelphia",
        "San Antonio",
        "San Diego",
        "Dallas",
        "San Jose",
        "Austin",
        "Jacksonville",
        "San Francisco",
        "Indianapolis",
        "Columbus",
        "Fort Worth",
        "Charlotte",
        "Seattle",
        "Denver",
        "Washington, D.C."
    )
}