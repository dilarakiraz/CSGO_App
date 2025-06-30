package com.dilara.csgo_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilara.csgo_app.domain.model.Skin
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

data class SkinFilterSortState(
    val selectedRarities: Set<String> = emptySet(),
    val selectedCollection: String? = null,
    val priceRange: ClosedFloatingPointRange<Float> = 0f..1000f,
    val selectedPriceRange: ClosedFloatingPointRange<Float> = 0f..1000f,
    val sortOption: SortOption = SortOption.AZ
)

@HiltViewModel
class SkinsViewModel @Inject constructor(
    private val repository: CsgoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Skin>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Skin>>> = _uiState.asStateFlow()

    private val _filterSortState = MutableStateFlow(SkinFilterSortState())
    val filterSortState: StateFlow<SkinFilterSortState> = _filterSortState.asStateFlow()

    init {
        loadSkins("tr") // Default to Turkish
    }

    fun loadSkins(language: String = "tr") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val skins = repository.getSkins(language)
                _uiState.value = UiState.Success(skins)
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

    fun updateCollection(collection: String) {
        _filterSortState.update { it.copy(selectedCollection = collection) }
    }

    fun updatePriceRange(range: ClosedFloatingPointRange<Float>) {
        _filterSortState.update { it.copy(selectedPriceRange = range) }
    }

    fun updateSortOption(option: SortOption) {
        _filterSortState.update { it.copy(sortOption = option) }
    }

    fun clearFilters() {
        _filterSortState.value = SkinFilterSortState()
    }
} 