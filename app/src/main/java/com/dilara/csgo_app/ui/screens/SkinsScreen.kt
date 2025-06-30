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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.dilara.csgo_app.domain.model.Category
import com.dilara.csgo_app.domain.model.Pattern
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.Team
import com.dilara.csgo_app.domain.model.Weapon
import com.dilara.csgo_app.ui.common.SortOption
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.FilterSortSheet
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar
import com.dilara.csgo_app.ui.theme.CsgoGradientEnd
import com.dilara.csgo_app.ui.theme.CsgoGradientStart
import com.dilara.csgo_app.ui.viewmodels.SkinsViewModel

@Composable
fun SkinsScreen(
    uiState: UiState<List<Skin>>,
    onRetry: () -> Unit,
    onSkinClick: (Skin) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SkinsViewModel = hiltViewModel()
) {
    val filterSortState by viewModel.filterSortState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SkinsScreenAppBar(
                title = "CS:GO Skins",
                onBackClick = onBackClick,
                onSearchClick = null,
                onFilterClick = {
                    showDialog.value = true
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                CsgoGradientStart,
                                CsgoGradientEnd
                            )
                        )
                    )
            ) {
                val filteredSkins = (uiState as? UiState.Success)?.data
                    ?.filter { skin ->
                        (filterSortState.selectedRarities.isEmpty() || skin.rarity.name in filterSortState.selectedRarities) &&
                                (filterSortState.selectedCollection == null || skin.collections.any { it.name == filterSortState.selectedCollection })
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
                        if (filteredSkins.isEmpty()) {
                            EmptyScreen(
                                icon = Icons.Default.Search,
                                title = "No Skins Found",
                                description = "No weapon skins are available at the moment.",
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
                                itemsIndexed(filteredSkins) { index, skin ->
                                    ModernItemCard(
                                        title = skin.name,
                                        subtitle = skin.weapon.name,
                                        imageUrl = skin.image,
                                        rarityName = skin.rarity.name,
                                        rarityColor = skin.rarity.color,
                                        onClick = { onSkinClick(skin) }
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
                        collectionOptions = (uiState as? UiState.Success)?.data
                            ?.flatMap { it.collections.map { col -> col.name } }
                            ?.distinct() ?: emptyList(),
                        selectedCollection = filterSortState.selectedCollection,
                        onCollectionChange = { viewModel.updateCollection(it) },
                        priceRange = filterSortState.priceRange,
                        selectedPriceRange = filterSortState.selectedPriceRange,
                        onPriceChange = { viewModel.updatePriceRange(it) },
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
                        showPriceFilter = true
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkinsScreenPreview() {
    val sampleSkins = listOf(
        Skin(
            id = "1",
            name = "AK-47 | Asiimov",
            description = "A legendary skin",
            weapon = Weapon("ak47", 1, "AK-47"),
            category = Category("rifle", "Rifle"),
            pattern = Pattern("", ""),
            minFloat = 0.0,
            maxFloat = 1.0,
            rarity = Rarity("covert", "Covert", "#EB4B4B"),
            stattrak = true,
            souvenir = false,
            paintIndex = "",
            wears = emptyList(),
            collections = emptyList(),
            crates = emptyList(),
            team = Team("", ""),
            legacyModel = false,
            image = "https://picsum.photos/300/200?random=1"
        ),
        Skin(
            id = "2",
            name = "M4A4 | Howl",
            description = "A rare skin",
            weapon = Weapon("m4a4", 2, "M4A4"),
            category = Category("rifle", "Rifle"),
            pattern = Pattern("", ""),
            minFloat = 0.0,
            maxFloat = 1.0,
            rarity = Rarity("contraband", "Contraband", "#FFD700"),
            stattrak = false,
            souvenir = false,
            paintIndex = "",
            wears = emptyList(),
            collections = emptyList(),
            crates = emptyList(),
            team = Team("", ""),
            legacyModel = false,
            image = "https://picsum.photos/300/200?random=2"
        )
    )

    SkinsScreen(
        uiState = UiState.Success(sampleSkins),
        onRetry = {},
        onSkinClick = {},
        onBackClick = {},
        onNavigateToHome = {}
    )
} 