package com.dilara.csgo_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilara.csgo_app.domain.model.Crate
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

data class CrateFilterSortState(
    val selectedRarities: Set<String> = emptySet(),
    val selectedType: String? = null,
    val sortOption: SortOption = SortOption.AZ
)

@HiltViewModel
class CratesViewModel @Inject constructor(
    private val repository: CsgoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Crate>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Crate>>> = _uiState.asStateFlow()

    private val _filterSortState = MutableStateFlow(CrateFilterSortState())
    val filterSortState: StateFlow<CrateFilterSortState> = _filterSortState.asStateFlow()

    init {
        loadCrates("tr")
    }

    fun loadCrates(language: String = "tr") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val crates = repository.getCrates(language)
                _uiState.value = UiState.Success(crates)
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

    fun updateType(type: String) {
        _filterSortState.update { it.copy(selectedType = if (it.selectedType == type) null else type) }
    }

    fun updateSortOption(option: SortOption) {
        _filterSortState.update { it.copy(sortOption = option) }
    }

    fun clearFilters() {
        _filterSortState.value = CrateFilterSortState()
    }
} 