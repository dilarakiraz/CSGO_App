package com.dilara.csgo_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.repository.CsgoRepository
import com.dilara.csgo_app.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkinsViewModel @Inject constructor(
    private val repository: CsgoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Skin>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Skin>>> = _uiState.asStateFlow()

    init {
        loadSkins()
    }

    fun loadSkins() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val skins = repository.getSkins()
                _uiState.value = UiState.Success(skins)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
} 