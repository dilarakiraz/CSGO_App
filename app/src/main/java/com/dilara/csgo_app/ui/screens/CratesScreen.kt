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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CratesScreen(
    uiState: UiState<List<Crate>>,
    onRetry: () -> Unit,
    onCrateClick: (Crate) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Column {
        SkinsScreenAppBar(
            title = "Crates",
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
                            items(uiState.data) { crate ->
                                ModernItemCard(
                                    title = crate.name,
                                    subtitle = crate.type,
                                    imageUrl = crate.image,
                                    rarityName = "Case",
                                    rarityColor = "#FFD700",
                                    onClick = { onCrateClick(crate) }
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