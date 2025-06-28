package com.dilara.csgo_app.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ModernAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onHomeClick: (() -> Unit)? = null,
    onFavoriteClick: (() -> Unit)? = null,
    isFavorite: Boolean = false,
    showFavorite: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A).copy(alpha = 0.95f),
                        Color(0xFF2D2D2D).copy(alpha = 0.9f)
                    )
                )
            )
            .shadow(12.dp, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Sol taraf - Geri butonu
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            Color.White.copy(alpha = 0.1f),
                            CircleShape
                        )
                        .shadow(4.dp, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(44.dp))
            }

            // Orta - Başlık
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Sağ taraf - Butonlar
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (onHomeClick != null) {
                    IconButton(
                        onClick = onHomeClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                Color(0xFF4B69FF).copy(alpha = 0.8f),
                                CircleShape
                            )
                            .shadow(4.dp, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (showFavorite && onFavoriteClick != null) {
                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                if (isFavorite) Color(0xFFEB4B4B).copy(alpha = 0.8f)
                                else Color.White.copy(alpha = 0.1f),
                                CircleShape
                            )
                            .shadow(4.dp, CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModernDetailTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        ),
        modifier = modifier,
        maxLines = 2
    )
}

@Composable
fun ModernDetailSubtitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            color = Color.White,
            fontWeight = FontWeight.Medium
        ),
        modifier = modifier
            .background(
                Color.Black.copy(alpha = 0.3f),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
fun ModernDetailDescription(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = Color.White.copy(alpha = 0.7f),
            fontWeight = FontWeight.Normal
        ),
        modifier = modifier
    )
}

// Preview Composables
@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ModernAppBarPreview() {
    ModernAppBar(
        title = "AK-47 | Asiimov",
        onBackClick = {},
        onHomeClick = {},
        onFavoriteClick = {},
        isFavorite = true,
        showFavorite = true
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ModernAppBarWithoutFavoritePreview() {
    ModernAppBar(
        title = "Skin Detail",
        onBackClick = {},
        onHomeClick = {},
        isFavorite = false,
        showFavorite = false
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ModernDetailTitlePreview() {
    ModernDetailTitle(
        text = "AK-47 | Asiimov"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ModernDetailSubtitlePreview() {
    ModernDetailSubtitle(
        text = "A futuristic design with orange and white colors"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ModernDetailDescriptionPreview() {
    ModernDetailDescription(
        text = "This skin features a sleek futuristic design with vibrant orange and white colors, making it one of the most popular skins in CS:GO."
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ModernDetailComponentsCombinedPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ModernDetailTitle(text = "AK-47 | Asiimov")
        ModernDetailSubtitle(text = "Futuristic Design")
        ModernDetailDescription(
            text = "A sleek futuristic design with vibrant orange and white colors."
        )
    }
} 