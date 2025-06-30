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
import androidx.compose.material.icons.filled.Warning
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
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.ui.common.SortOption
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.FilterSortSheet
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar
import com.dilara.csgo_app.ui.viewmodels.CratesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CratesScreen(
    uiState: UiState<List<Crate>>,
    onRetry: () -> Unit,
    onCrateClick: (Crate) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: CratesViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val filterSortState by viewModel.filterSortState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column {
            SkinsScreenAppBar(
                title = "Crates",
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
                val filteredCrates = (uiState as? UiState.Success)?.data
                    ?.filter { crate ->
                        (filterSortState.selectedType == null || crate.type == filterSortState.selectedType)
                    }
                    ?.sortedWith(
                        when (filterSortState.sortOption) {
                            SortOption.AZ -> compareBy { it.name }
                            SortOption.ZA -> compareByDescending { it.name }
                            else -> compareBy { it.name }
                        }
                    ) ?: emptyList()

                when (uiState) {
                    is UiState.Loading -> LoadingView()
                    is UiState.Success -> {
                        if (filteredCrates.isEmpty()) {
                            EmptyScreen(
                                icon = Icons.Default.Warning,
                                title = "No Cases Found",
                                description = "No weapon cases are available at the moment.",
                                onActionClick = onRetry,
                                actionText = "Retry"
                            )
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(filteredCrates) { crate ->
                                    ModernItemCard(
                                        title = crate.name,
                                        subtitle = crate.type,
                                        imageUrl = crate.image,
                                        rarityName = crate.type,
                                        rarityColor = "#FFD700",
                                        onClick = { onCrateClick(crate) }
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
                        rarityOptions = emptyList(),
                        selectedRarities = emptySet(),
                        onRarityChange = {},
                        collectionOptions = (uiState as? UiState.Success)?.data?.map { it.type }?.distinct() ?: emptyList(),
                        selectedCollection = filterSortState.selectedType,
                        onCollectionChange = { viewModel.updateType(it) },
                        priceRange = 0f..0f,
                        selectedPriceRange = 0f..0f,
                        onPriceChange = {},
                        sortOptions = listOf("A-Z", "Z-A"),
                        selectedSort = when (filterSortState.sortOption) {
                            SortOption.AZ -> "A-Z"
                            SortOption.ZA -> "Z-A"
                            else -> "A-Z"
                        },
                        onSortChange = {
                            viewModel.updateSortOption(
                                when (it) {
                                    "A-Z" -> SortOption.AZ
                                    "Z-A" -> SortOption.ZA
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
fun CratesScreenPreview() {
    val sampleCrates = listOf(
        Crate(
            id = "1",
            name = "Prisma Case",
            description = "A case full of colorful skins",
            type = "Weapon Case",
            firstSaleDate = "2019-03-13",
            contains = emptyList(),
            containsRare = emptyList(),
            marketHashName = "Prisma Case",
            rental = false,
            image = "https://picsum.photos/300/200?random=7",
            modelPlayer = "",
            lootList = null
        ),
        Crate(
            id = "2",
            name = "Danger Zone Case",
            description = "A case with tactical skins",
            type = "Weapon Case",
            firstSaleDate = "2018-12-06",
            contains = emptyList(),
            containsRare = emptyList(),
            marketHashName = "Danger Zone Case",
            rental = false,
            image = "https://picsum.photos/300/200?random=8",
            modelPlayer = "",
            lootList = null
        )
    )

    CratesScreen(
        uiState = UiState.Success(sampleCrates),
        onRetry = {},
        onCrateClick = {},
        onBackClick = {},
        onNavigateToHome = {}
    )
} 