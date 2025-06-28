package com.dilara.csgo_app.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.Sticker
import com.dilara.csgo_app.ui.components.ModernAppBar
import com.dilara.csgo_app.ui.components.ModernDetailSubtitle
import com.dilara.csgo_app.ui.components.ModernDetailTitle
import com.dilara.csgo_app.ui.components.RarityBadge
import com.dilara.csgo_app.ui.viewmodels.FavoritesViewModel
import com.dilara.csgo_app.util.cleanDescription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickerDetailScreen(
    sticker: Sticker?,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf<String?>(null) }
    val isFavorite = sticker?.id?.let { favoritesViewModel.isFavorite(it) } == true

    showToast?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            showToast = null
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1A1A1A), Color(0xFF232526))
                    )
                )
                .zIndex(2f)
        ) {
            ModernAppBar(
                title = sticker?.name ?: "Sticker Detail",
                onBackClick = onBackClick,
                onHomeClick = onNavigateToHome,
                onFavoriteClick = {
                    if (sticker != null) {
                        if (isFavorite) {
                            favoritesViewModel.toggleFavorite(context, sticker.id)
                            showToast = "Favorilerden çıkarıldı"
                        } else {
                            favoritesViewModel.addFavoriteSticker(context, sticker)
                            showToast = "Favorilere eklendi"
                        }
                    }
                },
                isFavorite = isFavorite,
                showFavorite = sticker != null
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 88.dp)
                .zIndex(1f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            if (sticker == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(24.dp)
                            .shadow(16.dp, RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Sticker not found",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "The requested sticker could not be loaded",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    // Hero Image Section
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .zIndex(1f)
                        ) {
                            // Background blur effect
                            AsyncImage(
                                model = sticker.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer { alpha = 0.3f },
                                contentScale = ContentScale.Crop
                            )
                            // Gradient overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(alpha = 0.3f),
                                                Color.Black.copy(alpha = 0.7f)
                                            )
                                        )
                                    )
                            )
                            // Main image
                            AsyncImage(
                                model = sticker.image,
                                contentDescription = sticker.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(48.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    // Content Section
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = (-40).dp)
                                .zIndex(2f)
                        ) {
                            ModernDetailTitle(text = sticker.name)
                            if (sticker.description.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                ModernDetailSubtitle(text = sticker.description.cleanDescription())
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Title and Rarity Card
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
                                    animationSpec = tween(600),
                                    initialOffsetY = { it / 2 }
                                ),
                                exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
                                    animationSpec = tween(300),
                                    targetOffsetY = { it / 2 }
                                )
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .shadow(16.dp, RoundedCornerShape(24.dp)),
                                    shape = RoundedCornerShape(24.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Text(
                                            text = sticker.name,
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        RarityBadge(
                                            rarityName = sticker.rarity.name,
                                            rarityColor = sticker.rarity.color
                                        )
                                        if (sticker.description.isNotEmpty()) {
                                            Text(
                                                text = sticker.description,
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Sticker Info Card
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(animationSpec = tween(800)) + slideInVertically(
                                    animationSpec = tween(800),
                                    initialOffsetY = { it / 2 }
                                ),
                                exit = fadeOut(animationSpec = tween(300))
                            ) {
                                ModernInfoCard(
                                    title = "Sticker Information",
                                    content = {
                                        ModernInfoRow("Type", sticker.type ?: "N/A")
                                        ModernInfoRow("Market Hash Name", sticker.marketHashName)
                                        if (sticker.tournamentEvent?.isNotEmpty() == true) {
                                            ModernInfoRow("Tournament Event", sticker.tournamentEvent)
                                        }
                                        if (sticker.tournamentTeam?.isNotEmpty() == true) {
                                            ModernInfoRow("Tournament Team", sticker.tournamentTeam)
                                        }
                                        if (sticker.effect?.isNotEmpty() == true) {
                                            ModernInfoRow("Effect", sticker.effect)
                                        }
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Crates Card
                            if (sticker.crates.isNotEmpty()) {
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                                        animationSpec = tween(1000),
                                        initialOffsetY = { it / 2 }
                                    ),
                                    exit = fadeOut(animationSpec = tween(300))
                                ) {
                                    ModernInfoCard(
                                        title = "Available in Crates",
                                        content = {
                                            sticker.crates.forEach { crate ->
                                                ModernInfoRow("", "• ${crate.name}")
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
    }
}

@Composable
private fun ModernInfoCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(12.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            content()
        }
    }
}

@Composable
private fun ModernInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StickerDetailScreenPreview() {
    val fakeFavoritesViewModel = FavoritesViewModel()
    StickerDetailScreen(
        sticker = Sticker(
            id = "1",
            name = "Sticker | FaZe Clan",
            description = "A cool sticker.",
            rarity = Rarity("gold", "Gold", "#FFD700"),
            crates = emptyList(),
            tournamentEvent = null,
            tournamentTeam = null,
            type = "Sticker",
            marketHashName = "Sticker | FaZe Clan",
            effect = null,
            image = "https://picsum.photos/300/200?random=31"
        ),
        onBackClick = {},
        onNavigateToHome = {},
        favoritesViewModel = fakeFavoritesViewModel
    )
}