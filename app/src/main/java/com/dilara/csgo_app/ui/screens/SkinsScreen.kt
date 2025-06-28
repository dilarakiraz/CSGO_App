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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.domain.model.Category
import com.dilara.csgo_app.domain.model.Pattern
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.Team
import com.dilara.csgo_app.domain.model.Weapon
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar
import com.dilara.csgo_app.ui.theme.CsgoGradientEnd
import com.dilara.csgo_app.ui.theme.CsgoGradientStart

@Composable
fun SkinsScreen(
    uiState: UiState<List<Skin>>,
    onRetry: () -> Unit,
    onSkinClick: (Skin) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column {
        SkinsScreenAppBar(
            title = "CS:GO Skins",
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
                            CsgoGradientStart,
                            CsgoGradientEnd
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
                            itemsIndexed(uiState.data) { index, skin ->
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