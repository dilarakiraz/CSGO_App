package com.dilara.csgo_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.Sticker
import com.dilara.csgo_app.ui.common.SortOption
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.FilterSortSheet
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar
import com.dilara.csgo_app.ui.viewmodels.StickersViewModel

@Composable
fun StickersScreen(
    uiState: UiState<List<Sticker>>,
    onRetry: () -> Unit,
    onStickerClick: (Sticker) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: StickersViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val filterSortState by viewModel.filterSortState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column {
            SkinsScreenAppBar(
                title = "Stickers",
                onBackClick = onBackClick,
                onSearchClick = null,
                onFilterClick = { showDialog.value = true }
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
                val filteredStickers = (uiState as? UiState.Success)?.data
                    ?.filter { sticker ->
                        (filterSortState.selectedRarities.isEmpty() || sticker.rarity.name in filterSortState.selectedRarities)
                    }
                    ?.sortedWith(
                        when (filterSortState.sortOption) {
                            SortOption.AZ -> compareBy { it.name }
                            SortOption.ZA -> compareByDescending { it.name }
                            SortOption.RARITY_ASC -> compareBy { it.rarity.name }
                            SortOption.RARITY_DESC -> compareByDescending { it.rarity.name }
                        }
                    ) ?: emptyList()

                when (uiState) {
                    is UiState.Loading -> LoadingView()
                    is UiState.Success -> {
                        if (filteredStickers.isEmpty()) {
                            EmptyScreen(
                                icon = Icons.Default.Star,
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
                                items(filteredStickers) { sticker ->
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

                    is UiState.Error -> ErrorView(message = uiState.message, onRetry = onRetry)
                }
            }
        }
        if (showDialog.value) {
            Box(
                Modifier
                    .fillMaxSize()
                    .zIndex(2f)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.45f))
                        .clickable(onClick = { showDialog.value = false })
                ) {}
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.95f)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(Modifier.padding(top = 8.dp, end = 8.dp)) {
                        IconButton(
                            onClick = { showDialog.value = false },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Kapat")
                        }
                    }
                    FilterSortSheet(
                        rarityOptions = (uiState as? UiState.Success)?.data?.map { it.rarity.name }?.distinct() ?: emptyList(),
                        selectedRarities = filterSortState.selectedRarities,
                        onRarityChange = { viewModel.updateRarity(it) },
                        collectionOptions = emptyList(),
                        selectedCollection = null,
                        onCollectionChange = {},
                        priceRange = 0f..0f,
                        selectedPriceRange = 0f..0f,
                        onPriceChange = {},
                        sortOptions = listOf("A-Z", "Z-A", "Rarity ↑", "Rarity ↓"),
                        selectedSort = when (filterSortState.sortOption) {
                            SortOption.AZ -> "A-Z"
                            SortOption.ZA -> "Z-A"
                            SortOption.RARITY_ASC -> "Rarity ↑"
                            SortOption.RARITY_DESC -> "Rarity ↓"
                        },
                        onSortChange = {
                            viewModel.updateSortOption(
                                when (it) {
                                    "A-Z" -> SortOption.AZ
                                    "Z-A" -> SortOption.ZA
                                    "Rarity ↑" -> SortOption.RARITY_ASC
                                    "Rarity ↓" -> SortOption.RARITY_DESC
                                    else -> SortOption.AZ
                                }
                            )
                        },
                        onClear = { viewModel.clearFilters() },
                        onApply = { showDialog.value = false },
                        onDismiss = { showDialog.value = false },
                        showPriceFilter = false
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