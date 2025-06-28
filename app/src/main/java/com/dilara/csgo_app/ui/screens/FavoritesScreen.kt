package com.dilara.csgo_app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ModernAppBar
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.viewmodels.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    onItemClick: (String, String) -> Unit = { _, _ -> }, // type, id
    favoritesViewModel: FavoritesViewModel
) {
    val favorites = favoritesViewModel.favoriteItems.value
    val context = LocalContext.current
    var showToast by remember { mutableStateOf<String?>(null) }
    showToast?.let { message ->
        androidx.compose.runtime.LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            showToast = null
        }
    }
    androidx.compose.material3.Scaffold(
        topBar = {
            ModernAppBar(
                title = "Favorites",
                onBackClick = onBackClick,
                onHomeClick = onNavigateToHome,
                showFavorite = false
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            if (favorites.isEmpty()) {
                EmptyScreen(
                    icon = Icons.Default.Favorite,
                    title = "No Favorites Yet",
                    description = "Items you favorite will appear here. Start exploring and add some items to your favorites!",
                    onActionClick = onNavigateToHome,
                    actionText = "Explore Items"
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(favorites) { favorite ->
                        ModernItemCard(
                            title = favorite.name,
                            subtitle = favorite.subtitle,
                            imageUrl = favorite.imageUrl,
                            rarityName = favorite.rarityName,
                            rarityColor = favorite.rarityColor,
                            isFavorite = true,
                            onFavoriteClick = {
                                favoritesViewModel.toggleFavorite(context, favorite.id)
                                showToast = "Favorilerden çıkarıldı"
                            },
                            onClick = { onItemClick(favorite.type, favorite.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    val fakeFavoritesViewModel = FavoritesViewModel()
    FavoritesScreen(
        onBackClick = {},
        onNavigateToHome = {},
        onItemClick = { _, _ -> },
        favoritesViewModel = fakeFavoritesViewModel
    )
} 