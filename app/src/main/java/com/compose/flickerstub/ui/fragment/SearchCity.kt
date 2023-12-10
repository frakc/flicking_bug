package com.compose.flickerstub.ui.fragment

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class SearchCity(
    val recent: List<String> = emptyList(),
    val fromServer: List<String> = emptyList(),
    val exception: Exception? = null
) {
}