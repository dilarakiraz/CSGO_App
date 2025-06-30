package com.dilara.csgo_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.repository.CsgoRepository
import com.dilara.csgo_app.ui.common.SortOption
import com.dilara.csgo_app.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgentsViewModel @Inject constructor(
    private val repository: CsgoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Agent>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agent>>> = _uiState.asStateFlow()

    private val _filterSortState = MutableStateFlow(AgentFilterSortState())
    val filterSortState: StateFlow<AgentFilterSortState> = _filterSortState.asStateFlow()

    init {
        loadAgents("tr")
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

    fun updateRarity(rarity: String) {
        _filterSortState.update { state ->
            val newSet = if (rarity in state.selectedRarities) state.selectedRarities - rarity else state.selectedRarities + rarity
            state.copy(selectedRarities = newSet)
        }
    }

    fun updateTeam(team: String) {
        _filterSortState.update { it.copy(selectedTeam = if (it.selectedTeam == team) null else team) }
    }

    fun updateSortOption(option: SortOption) {
        _filterSortState.update { it.copy(sortOption = option) }
    }

    fun clearFilters() {
        _filterSortState.value = AgentFilterSortState()
    }
}

data class AgentFilterSortState(
    val selectedRarities: Set<String> = emptySet(),
    val selectedTeam: String? = null,
    val sortOption: SortOption = SortOption.AZ
) 