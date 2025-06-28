package com.dilara.csgo_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.repository.CsgoRepository
import com.dilara.csgo_app.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgentsViewModel @Inject constructor(
    private val repository: CsgoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Agent>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agent>>> = _uiState.asStateFlow()

    init {
        loadAgents("tr") // Default to Turkish
    }

    fun loadAgents(language: String = "tr") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val agents = repository.getAgents(language)
                _uiState.value = UiState.Success(agents)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
} 