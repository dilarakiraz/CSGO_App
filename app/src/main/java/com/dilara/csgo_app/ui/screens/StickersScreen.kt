package com.dilara.csgo_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.domain.model.Sticker
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.ItemCard
import com.dilara.csgo_app.ui.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickersScreen(
    uiState: UiState<List<Sticker>>,
    onRetry: () -> Unit,
    onStickerClick: (Sticker) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Stickers",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingView()
            }
            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(uiState.data) { sticker ->
                        ItemCard(
                            name = sticker.name,
                            imageUrl = sticker.image,
                            rarityName = sticker.rarity.name,
                            rarityColor = sticker.rarity.color,
                            onClick = { onStickerClick(sticker) }
                        )
                    }
                }
            }
            is UiState.Error -> {
                ErrorView(
                    message = uiState.message,
                    onRetry = onRetry
                )
            }
        }
    }
} 