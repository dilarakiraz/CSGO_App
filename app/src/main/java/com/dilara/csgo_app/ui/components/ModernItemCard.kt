package com.dilara.csgo_app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

enum class FavoriteIconStyle { Default, Prominent }

@Composable
fun ModernItemCard(
    title: String,
    subtitle: String,
    imageUrl: String = "",
    rarityName: String,
    rarityColor: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavoriteClick: (() -> Unit)? = null,
    painter: Painter? = null,
    favoriteIconPainter: Painter? = null,
    favoriteStyle: FavoriteIconStyle = FavoriteIconStyle.Default
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .scale(scale)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Gradient Overlay with modern design
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 0f,
                            endY = 1000f
                        )
                    )
            )

            if (onFavoriteClick != null && isFavorite && favoriteStyle == FavoriteIconStyle.Prominent) {
                androidx.compose.material3.IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(44.dp)
                        .background(Color(0xFFEB4B4B).copy(alpha = 0.8f), RoundedCornerShape(50))
                        .shadow(6.dp, RoundedCornerShape(50))
                        .zIndex(2f)
                ) {
                    if (favoriteIconPainter != null) {
                        androidx.compose.material3.Icon(
                            painter = favoriteIconPainter,
                            contentDescription = "Favoriler",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    } else {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.Favorite,
                            contentDescription = "Favoriler",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp, start = 14.dp, end = 14.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ModernRarityBadge(
                    rarityName = rarityName,
                    rarityColor = rarityColor
                )
                if (onFavoriteClick != null && (favoriteStyle == FavoriteIconStyle.Default || !isFavorite)) {
                    Spacer(modifier = Modifier.width(8.dp))
                    androidx.compose.material3.IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                Color.Transparent,
                                RoundedCornerShape(50)
                            )
                    ) {
                        if (isFavorite && favoriteIconPainter != null) {
                            androidx.compose.material3.Icon(
                                painter = favoriteIconPainter,
                                contentDescription = "Favoriler",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            androidx.compose.material3.Icon(
                                imageVector = if (isFavorite) androidx.compose.material.icons.Icons.Filled.Favorite else androidx.compose.material.icons.Icons.Filled.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun ModernRarityBadge(
    rarityName: String,
    rarityColor: String,
    modifier: Modifier = Modifier
) {
    // Convert hex color to Color and determine text color for better readability
    val backgroundColor = try {
        Color(android.graphics.Color.parseColor(rarityColor))
    } catch (e: Exception) {
        Color.Gray
    }

    val finalBackgroundColor = when (rarityColor.uppercase()) {
        "#FFD700", "#FFFF00", "#FFEB3B" -> Color(0xFF8B6914) // Dark gold
        "#FF6B6B", "#FF4444", "#FF0000" -> Color(0xFF8B0000) // Dark red
        "#4B69FF", "#2196F3", "#03A9F4" -> Color(0xFF1565C0) // Dark blue
        "#4CAF50", "#8BC34A", "#CDDC39" -> Color(0xFF2E7D32) // Dark green
        "#9C27B0", "#E91E63", "#FF5722" -> Color(0xFF6A1B9A) // Dark purple
        "#FF9800", "#FFC107" -> Color(0xFFE65100) // Dark orange
        else -> backgroundColor
    }

    val textColor = Color.White

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = finalBackgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = rarityName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
} 