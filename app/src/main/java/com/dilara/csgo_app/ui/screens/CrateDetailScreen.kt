package com.dilara.csgo_app.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.ui.components.ModernAppBar
import com.dilara.csgo_app.ui.components.ModernDetailSubtitle
import com.dilara.csgo_app.ui.components.ModernDetailTitle
import com.dilara.csgo_app.ui.components.RarityBadge
import com.dilara.csgo_app.ui.viewmodels.FavoritesViewModel
import com.dilara.csgo_app.util.cleanDescription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrateDetailScreen(
    crate: Crate?,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf<String?>(null) }
    val isFavorite = crate?.id?.let { favoritesViewModel.isFavorite(it) } == true

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
                title = crate?.name ?: "Crate Detail",
                onBackClick = onBackClick,
                onHomeClick = onNavigateToHome,
                onFavoriteClick = {
                    if (crate != null) {
                        if (isFavorite) {
                            favoritesViewModel.toggleFavorite(context, crate.id)
                            showToast = "Favorilerden çıkarıldı"
                        } else {
                            favoritesViewModel.addFavoriteCrate(context, crate)
                            showToast = "Favorilere eklendi"
                        }
                    }
                },
                isFavorite = isFavorite,
                showFavorite = crate != null
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
            if (crate == null) {
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
                                text = "Crate not found",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "The requested crate could not be loaded",
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
                                model = crate.image,
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
                                model = crate.image,
                                contentDescription = crate.name,
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
                            ModernDetailTitle(text = crate.name)
                            if (!crate.description.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                ModernDetailSubtitle(text = crate.description.cleanDescription())
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Crate Info Card
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(animationSpec = tween(800)) + slideInVertically(
                                    animationSpec = tween(800),
                                    initialOffsetY = { it / 2 }
                                ),
                                exit = fadeOut(animationSpec = tween(300))
                            ) {
                                ModernInfoCard(
                                    title = "Crate Information",
                                    content = {
                                        ModernInfoRow("Type", crate.type)
                                        ModernInfoRow("Market Hash Name", crate.marketHashName)
                                        if (crate.firstSaleDate?.isNotEmpty() == true) {
                                            ModernInfoRow("First Sale Date", crate.firstSaleDate)
                                        }
                                        if (crate.modelPlayer?.isNotEmpty() == true) {
                                            ModernInfoRow("Model Player", crate.modelPlayer)
                                        }
                                        ModernInfoRow("Rental", if (crate.rental == true) "Yes" else "No")
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Contains Items Card
                            if (crate.contains.isNotEmpty()) {
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                                        animationSpec = tween(1000),
                                        initialOffsetY = { it / 2 }
                                    ),
                                    exit = fadeOut(animationSpec = tween(300))
                                ) {
                                    ModernInfoCard(
                                        title = "Contains Items",
                                        content = {
                                            crate.contains.forEach { item ->
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = item.name,
                                                        style = MaterialTheme.typography.bodyMedium
                                                    )
                                                    RarityBadge(
                                                        rarityName = item.rarity.name,
                                                        rarityColor = item.rarity.color
                                                    )
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Contains Rare Items Card
                            if (crate.containsRare.isNotEmpty()) {
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(1200)) + slideInVertically(
                                        animationSpec = tween(1200),
                                        initialOffsetY = { it / 2 }
                                    ),
                                    exit = fadeOut(animationSpec = tween(300))
                                ) {
                                    ModernInfoCard(
                                        title = "Rare Items",
                                        content = {
                                            crate.containsRare.forEach { item ->
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = item.name,
                                                        style = MaterialTheme.typography.bodyMedium
                                                    )
                                                    RarityBadge(
                                                        rarityName = item.rarity.name,
                                                        rarityColor = item.rarity.color
                                                    )
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Loot List Card
                            crate.lootList?.let { lootList ->
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(1400)) + slideInVertically(
                                        animationSpec = tween(1400),
                                        initialOffsetY = { it / 2 }
                                    ),
                                    exit = fadeOut(animationSpec = tween(300))
                                ) {
                                    ModernInfoCard(
                                        title = "Loot List",
                                        content = {
                                            ModernInfoRow("Name", lootList.name)
                                            if (lootList.footer.isNotEmpty()) {
                                                ModernInfoRow("Footer", lootList.footer)
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
fun CrateDetailScreenPreview() {
    val fakeFavoritesViewModel = FavoritesViewModel()
    CrateDetailScreen(
        crate = Crate(
            id = "1",
            name = "Prisma Case",
            description = "A weapon case.",
            type = "Weapon Case",
            firstSaleDate = "2020-01-01",
            contains = emptyList(),
            containsRare = emptyList(),
            marketHashName = "Prisma Case",
            rental = false,
            image = "https://picsum.photos/300/200?random=21",
            modelPlayer = null,
            lootList = null
        ),
        onBackClick = {},
        onNavigateToHome = {},
        favoritesViewModel = fakeFavoritesViewModel
    )
} 