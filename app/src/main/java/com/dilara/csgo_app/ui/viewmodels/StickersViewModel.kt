package com.dilara.csgo_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilara.csgo_app.domain.model.Sticker
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

data class StickerFilterSortState(
    val selectedRarities: Set<String> = emptySet(),
    val selectedCollection: String? = null,
    val sortOption: SortOption = SortOption.AZ
)

@HiltViewModel
class StickersViewModel @Inject constructor(
    private val repository: CsgoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Sticker>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Sticker>>> = _uiState.asStateFlow()

    private val _filterSortState = MutableStateFlow(StickerFilterSortState())
    val filterSortState: StateFlow<StickerFilterSortState> = _filterSortState.asStateFlow()

    init {
        loadStickers("tr") // Default to Turkish
    }

    fun loadStickers(language: String = "tr") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val stickers = repository.getStickers(language)
                _uiState.value = UiState.Success(stickers)
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
        _filterSortState.update { it.copy(selectedCollection = if (it.selectedCollection == collection) null else collection) }
    }

    fun updateSortOption(option: SortOption) {
        _filterSortState.update { it.copy(sortOption = option) }
    }

    fun clearFilters() {
        _filterSortState.value = StickerFilterSortState()
    }
} 