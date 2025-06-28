package com.dilara.csgo_app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.ModernTopAppBar
import com.dilara.csgo_app.ui.theme.CsgoGradientEnd
import com.dilara.csgo_app.ui.theme.CsgoGradientStart

data class HomeMenuItem(
    val title: String,
    val description: String,
    val imageUrl: String,
    val route: String,
    val gradientColors: List<Color>
)

@Composable
fun HomeScreen(
    onNavigateToSkins: () -> Unit,
    onNavigateToAgents: () -> Unit,
    onNavigateToStickers: () -> Unit,
    onNavigateToCrates: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onDrawerClick: () -> Unit = {}
) {
    val menuItems = listOf(
        HomeMenuItem(
            title = "Weapon Skins",
            description = "Browse all weapon skins",
            imageUrl = "https://picsum.photos/300/200?random=1",
            route = "skins",
            gradientColors = listOf(Color(0xFFE74C3C), Color(0xFFC0392B))
        ),
        HomeMenuItem(
            title = "Agents",
            description = "View character agents",
            imageUrl = "https://picsum.photos/300/200?random=2",
            route = "agents",
            gradientColors = listOf(Color(0xFF3498DB), Color(0xFF2980B9))
        ),
        HomeMenuItem(
            title = "Stickers",
            description = "Explore stickers and decals",
            imageUrl = "https://picsum.photos/300/200?random=3",
            route = "stickers",
            gradientColors = listOf(Color(0xFF9B59B6), Color(0xFF8E44AD))
        ),
        HomeMenuItem(
            title = "Cases",
            description = "Check weapon cases",
            imageUrl = "https://picsum.photos/300/200?random=4",
            route = "crates",
            gradientColors = listOf(Color(0xFFF39C12), Color(0xFFE67E22))
        ),
        HomeMenuItem(
            title = "Favorites",
            description = "Your favorite items",
            imageUrl = "https://picsum.photos/300/200?random=5",
            route = "favorites",
            gradientColors = listOf(Color(0xFF2ECC71), Color(0xFF27AE60))
        )
    )

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Scaffold(
        topBar = {
            ModernTopAppBar(
                title = "CS:GO Arsenal",
                showBackButton = false,
                showHomeButton = false,
                showDrawerButton = true,
                onDrawerClick = onDrawerClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            CsgoGradientStart,
                            CsgoGradientEnd
                        )
                    )
                )
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                    animationSpec = tween(1000),
                    initialOffsetY = { it }
                ),
                exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
                    animationSpec = tween(300),
                    targetOffsetY = { it }
                )
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    itemsIndexed(menuItems) { index, item ->
                        val delay = index * 100
                        var itemVisible by remember { mutableStateOf(false) }

                        LaunchedEffect(Unit) {
                            kotlinx.coroutines.delay(delay.toLong())
                            itemVisible = true
                        }

                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = fadeIn(animationSpec = tween(500, delayMillis = delay)) + slideInVertically(
                                animationSpec = tween(500, delayMillis = delay),
                                initialOffsetY = { it / 2 }
                            )
                        ) {
                            ModernItemCard(
                                title = item.title,
                                subtitle = item.description,
                                imageUrl = item.imageUrl,
                                rarityName = "Menu",
                                rarityColor = "#FF6B35",
                                onClick = {
                                    when (item.route) {
                                        "skins" -> onNavigateToSkins()
                                        "agents" -> onNavigateToAgents()
                                        "stickers" -> onNavigateToStickers()
                                        "crates" -> onNavigateToCrates()
                                        "favorites" -> onNavigateToFavorites()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onNavigateToSkins = {},
        onNavigateToAgents = {},
        onNavigateToStickers = {},
        onNavigateToCrates = {},
        onNavigateToFavorites = {}
    )
} 