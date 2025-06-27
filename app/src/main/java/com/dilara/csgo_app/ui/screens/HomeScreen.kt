package com.dilara.csgo_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.dilara.csgo_app.ui.components.ItemCard

data class HomeMenuItem(
    val title: String,
    val description: String,
    val imageUrl: String,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSkins: () -> Unit,
    onNavigateToAgents: () -> Unit,
    onNavigateToStickers: () -> Unit,
    onNavigateToCrates: () -> Unit
) {
    val menuItems = listOf(
        HomeMenuItem(
            title = "Weapon Skins",
            description = "Browse all weapon skins",
            imageUrl = "https://raw.githubusercontent.com/ByMykel/counter-strike-image-tracker/main/static/panorama/images/econ/default_generated/weapon_ak47_gs_ak47_asiimov_light_png.png",
            route = "skins"
        ),
        HomeMenuItem(
            title = "Agents",
            description = "View character agents",
            imageUrl = "https://raw.githubusercontent.com/ByMykel/counter-strike-image-tracker/main/static/panorama/images/econ/characters/customplayer_ctm_gsg9_varf5_png.png",
            route = "agents"
        ),
        HomeMenuItem(
            title = "Stickers",
            description = "Explore stickers and decals",
            imageUrl = "https://raw.githubusercontent.com/ByMykel/counter-strike-image-tracker/main/static/panorama/images/econ/stickers/stockh2021/faze_gold_png.png",
            route = "stickers"
        ),
        HomeMenuItem(
            title = "Cases",
            description = "Check weapon cases",
            imageUrl = "https://raw.githubusercontent.com/ByMykel/counter-strike-image-tracker/main/static/panorama/images/econ/weapon_cases/crate_valve_1_png.png",
            route = "crates"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CS:GO Arsenal",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(menuItems) { item ->
                ItemCard(
                    name = item.title,
                    imageUrl = item.imageUrl,
                    rarityName = "Menu",
                    rarityColor = "#FF6B35",
                    onClick = {
                        when (item.route) {
                            "skins" -> onNavigateToSkins()
                            "agents" -> onNavigateToAgents()
                            "stickers" -> onNavigateToStickers()
                            "crates" -> onNavigateToCrates()
                        }
                    }
                )
            }
        }
    }
} 