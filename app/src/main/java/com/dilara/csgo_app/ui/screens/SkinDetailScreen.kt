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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dilara.csgo_app.domain.model.Category
import com.dilara.csgo_app.domain.model.Pattern
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.SimpleCrate
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.SkinCollection
import com.dilara.csgo_app.domain.model.Team
import com.dilara.csgo_app.domain.model.Weapon
import com.dilara.csgo_app.domain.model.Wear
import com.dilara.csgo_app.ui.components.ModernAppBar
import com.dilara.csgo_app.ui.components.ModernDetailSubtitle
import com.dilara.csgo_app.ui.components.ModernDetailTitle
import com.dilara.csgo_app.ui.components.RarityBadge
import com.dilara.csgo_app.ui.viewmodels.FavoritesViewModel
import com.dilara.csgo_app.util.cleanDescription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkinDetailScreen(
    skin: Skin?,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf<String?>(null) }
    val isFavorite = skin?.id?.let { favoritesViewModel.isFavorite(it) } == true

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
                title = skin?.name ?: "Skin Detail",
                onBackClick = onBackClick,
                onHomeClick = onNavigateToHome,
                onFavoriteClick = {
                    if (skin != null) {
                        if (isFavorite) {
                            favoritesViewModel.toggleFavorite(context, skin.id)
                            showToast = "Favorilerden çıkarıldı"
                        } else {
                            favoritesViewModel.addFavoriteSkin(context, skin)
                            showToast = "Favorilere eklendi"
                        }
                    }
                },
                isFavorite = isFavorite,
                showFavorite = skin != null
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
            if (skin == null) {
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
                                text = "Skin not found",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "The requested skin could not be loaded",
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
                                .height(400.dp)
                                .zIndex(1f)
                        ) {
                            // Background blur effect
                            AsyncImage(
                                model = skin.image,
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
                                model = skin.image,
                                contentDescription = skin.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(32.dp),
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
                            ModernDetailTitle(text = skin.name)
                            if (skin.description.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                ModernDetailSubtitle(text = skin.description.cleanDescription())
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
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            RarityBadge(
                                                rarityName = skin.rarity.name,
                                                rarityColor = skin.rarity.color
                                            )

                                            if (skin.stattrak) {
                                                RarityBadge(
                                                    rarityName = "StatTrak",
                                                    rarityColor = "#FFD700"
                                                )
                                            }

                                            if (skin.souvenir) {
                                                RarityBadge(
                                                    rarityName = "Souvenir",
                                                    rarityColor = "#FF6B35"
                                                )
                                            }
                                        }

                                        if (skin.description.isNotEmpty()) {
                                            Text(
                                                text = skin.description,
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Weapon Info Card
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(animationSpec = tween(800)) + slideInVertically(
                                    animationSpec = tween(800),
                                    initialOffsetY = { it / 2 }
                                ),
                                exit = fadeOut(animationSpec = tween(300))
                            ) {
                                ModernInfoCard(
                                    title = "Weapon Information",
                                    content = {
                                        ModernInfoRow("Weapon", skin.weapon.name)
                                        ModernInfoRow("Category", skin.category.name)
                                        ModernInfoRow("Pattern", skin.pattern.name)
                                        ModernInfoRow("Paint Index", skin.paintIndex)
                                        ModernInfoRow("Float Range", "${skin.minFloat} - ${skin.maxFloat}")
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Collections Card
                            if (skin.collections.isNotEmpty()) {
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                                        animationSpec = tween(1000),
                                        initialOffsetY = { it / 2 }
                                    ),
                                    exit = fadeOut(animationSpec = tween(300))
                                ) {
                                    ModernInfoCard(
                                        title = "Collections",
                                        content = {
                                            skin.collections.forEach { collection ->
                                                ModernInfoRow("", "• ${collection.name}")
                                            }
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            // Crates Card
                            if (skin.crates.isNotEmpty()) {
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(1200)) + slideInVertically(
                                        animationSpec = tween(1200),
                                        initialOffsetY = { it / 2 }
                                    ),
                                    exit = fadeOut(animationSpec = tween(300))
                                ) {
                                    ModernInfoCard(
                                        title = "Available in Crates",
                                        content = {
                                            skin.crates.forEach { crate ->
                                                ModernInfoRow("", "• ${crate.name}")
                                            }
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            // Team Info Card
                            if (skin.team.name.isNotEmpty()) {
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(1400)) + slideInVertically(
                                        animationSpec = tween(1400),
                                        initialOffsetY = { it / 2 }
                                    ),
                                    exit = fadeOut(animationSpec = tween(300))
                                ) {
                                    ModernInfoCard(
                                        title = "Team Information",
                                        content = {
                                            ModernInfoRow("Team", skin.team.name)
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

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun SkinDetailScreenPreview() {
    val sampleSkin = Skin(
        id = "1",
        name = "AK-47 | Asiimov",
        description = "A futuristic design with orange and white colors",
        rarity = Rarity("covert", "Covert", "#EB4B4B"),
        weapon = Weapon("ak47", 1, "AK-47"),
        category = Category("rifle", "Rifle"),
        pattern = Pattern("asiimov", "Asiimov"),
        paintIndex = "180",
        minFloat = 0.0,
        maxFloat = 1.0,
        stattrak = true,
        souvenir = false,
        image = "https://picsum.photos/300/200?random=1",
        collections = listOf(
            SkinCollection("1", "The Bravo Collection", ""),
            SkinCollection("2", "Operation Bravo", "")
        ),
        crates = listOf(
            SimpleCrate("1", "Bravo Case", ""),
            SimpleCrate("2", "Operation Bravo Case", "")
        ),
        wears = listOf(
            Wear("1", "Factory New"),
            Wear("2", "Minimal Wear")
        ),
        team = Team("1", "Counter-Terrorist"),
        legacyModel = false
    )

    SkinDetailScreen(
        skin = sampleSkin,
        onBackClick = {},
        onNavigateToHome = {}
    )
} 