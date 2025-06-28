package com.dilara.csgo_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.Sticker
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickersScreen(
    uiState: UiState<List<Sticker>>,
    onRetry: () -> Unit,
    onStickerClick: (Sticker) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Column {
        SkinsScreenAppBar(
            title = "Stickers",
            onBackClick = onBackClick,
            onSearchClick = null
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    LoadingView()
                }

                is UiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        EmptyScreen(
                            icon = Icons.Filled.Star,
                            title = "No Stickers Found",
                            description = "No stickers are available at the moment.",
                            onActionClick = onRetry,
                            actionText = "Retry"
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(uiState.data) { sticker ->
                                ModernItemCard(
                                    title = sticker.name,
                                    subtitle = sticker.type ?: "Sticker",
                                    imageUrl = sticker.image,
                                    rarityName = sticker.rarity.name,
                                    rarityColor = sticker.rarity.color,
                                    onClick = { onStickerClick(sticker) }
                                )
                            }
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
}

@Preview(showBackground = true)
@Composable
fun StickersScreenPreview() {
    val sampleStickers = listOf(
        Sticker(
            id = "1",
            name = "Sticker | FaZe Clan (Gold)",
            description = "A rare gold sticker",
            rarity = Rarity("gold", "Gold", "#FFD700"),
            crates = emptyList(),
            tournamentEvent = "Stockholm 2021",
            tournamentTeam = "FaZe Clan",
            type = "Sticker",
            marketHashName = "Sticker | FaZe Clan (Gold) | Stockholm 2021",
            effect = "Gold",
            image = "https://picsum.photos/300/200?random=5"
        ),
        Sticker(
            id = "2",
            name = "Sticker | NAVI (Holo)",
            description = "A holographic sticker",
            rarity = Rarity("holo", "Holographic", "#4B69FF"),
            crates = emptyList(),
            tournamentEvent = "Stockholm 2021",
            tournamentTeam = "NAVI",
            type = "Sticker",
            marketHashName = "Sticker | NAVI (Holo) | Stockholm 2021",
            effect = "Holographic",
            image = "https://picsum.photos/300/200?random=6"
        )
    )

    StickersScreen(
        uiState = UiState.Success(sampleStickers),
        onRetry = {},
        onStickerClick = {},
        onBackClick = {},
        onNavigateToHome = {}
    )
} 