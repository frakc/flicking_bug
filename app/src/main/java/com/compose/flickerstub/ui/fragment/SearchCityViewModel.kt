package com.compose.flickerstub.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val serverUseCase: SearchCityUseCase,
) : ViewModel() {

    private var queryState = MutableStateFlow("")
    private val _state = MutableStateFlow(SearchCityState())
    val state: StateFlow<SearchCityState> = _state.asStateFlow()
    private var job: Job? = null

    init {
        viewModelScope.launch {
            queryState.debounce(350L).collect {
                job?.cancel()
                job = viewModelScope.launch {
                    serverUseCase.invoke(it).collect {
                        _state.update { state -> state.copy(searchCities = it) }
                    }
                }
            }
        }
    }

    fun searchCities(query: String) {
        _state.update { state -> state.copy(query = query) }

        queryState.value = query
    }
}