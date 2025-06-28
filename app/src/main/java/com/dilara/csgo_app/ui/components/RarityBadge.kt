package com.dilara.csgo_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RarityBadge(
    rarityName: String,
    rarityColor: String,
    modifier: Modifier = Modifier
) {
    val color = try {
        Color(android.graphics.Color.parseColor(rarityColor))
    } catch (e: Exception) {
        Color.Gray
    }

    Text(
        text = rarityName,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color.copy(alpha = 0.15f),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        color = color,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )
} 