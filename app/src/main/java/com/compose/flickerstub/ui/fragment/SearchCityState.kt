package com.compose.flickerstub.ui.fragment

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class SearchCityState(
    val query :String = "",
    val keyboardHeight: Int = 0,
    val searchCities: SearchCity = SearchCity()
) {
}