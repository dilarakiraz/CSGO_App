package com.dilara.csgo_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.R

fun rarityColor(rarity: String): Color = when (rarity.lowercase()) {
    "çok gizli", "covert" -> Color(0xFFEB4B4B)
    "gizli", "classified" -> Color(0xFFD32CE6)
    "askeri sınıf", "mil-spec" -> Color(0xFF4B69FF)
    "endüstri sınıfı", "industrial" -> Color(0xFF5E98D9)
    "sınırlı", "restricted" -> Color(0xFF8847FF)
    "olağandışı", "consumer" -> Color(0xFFB0C3D9)
    "fabrika yeni", "factory new" -> Color(0xFFFFD700)
    else -> Color.LightGray
}

@Composable
fun FilterSortSheet(
    rarityOptions: List<String>,
    selectedRarities: Set<String>,
    onRarityChange: (String) -> Unit,
    collectionOptions: List<String>,
    selectedCollection: String?,
    onCollectionChange: (String) -> Unit,
    priceRange: ClosedFloatingPointRange<Float>,
    selectedPriceRange: ClosedFloatingPointRange<Float>,
    onPriceChange: (ClosedFloatingPointRange<Float>) -> Unit,
    sortOptions: List<String>,
    selectedSort: String,
    onSortChange: (String) -> Unit,
    onClear: () -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit,
    showPriceFilter: Boolean = false
) {
    val density = LocalDensity.current
    val contentHeight = remember { mutableStateOf(0) }
    val scrollable = with(density) { contentHeight.value.toDp() > 600.dp }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .heightIn(min = 300.dp)
    ) {
        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp)
                    .width(48.dp)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.25f))
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Filtrele & Sırala",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Divider(Modifier.padding(vertical = 8.dp))
            Text("Rarity", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
            Spacer(Modifier.height(10.dp))
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                rarityOptions.forEach { rarity ->
                    val selected = rarity in selectedRarities
                    Surface(
                        color = if (selected) rarityColor(rarity) else Color.LightGray,
                        shape = RoundedCornerShape(50),
                        shadowElevation = if (selected) 6.dp else 0.dp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { onRarityChange(rarity) }
                    ) {
                        Text(
                            rarity,
                            color = if (selected) Color.White else Color.Black,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            Text("Koleksiyon", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                collectionOptions.forEach { collection ->
                    val selected = collection == selectedCollection
                    Surface(
                        color = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        shape = RoundedCornerShape(50),
                        shadowElevation = if (selected) 6.dp else 0.dp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { onCollectionChange(collection) }
                    ) {
                        Text(
                            collection,
                            color = if (selected) Color.White else Color.Black,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            if (showPriceFilter) {
                Text("Fiyat Aralığı", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                Column(Modifier.padding(horizontal = 8.dp)) {
                    RangeSlider(
                        value = selectedPriceRange,
                        onValueChange = onPriceChange,
                        valueRange = priceRange,
                        steps = 10
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "${selectedPriceRange.start.toInt()}₺",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "${selectedPriceRange.endInclusive.toInt()}₺",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Spacer(Modifier.height(18.dp))
            }
            Text("Sıralama", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                sortOptions.forEach { sort ->
                    val selected = sort == selectedSort
                    Surface(
                        color = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        shape = RoundedCornerShape(50),
                        shadowElevation = if (selected) 6.dp else 0.dp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { onSortChange(sort) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            if (sort.contains("A-Z")) {
                                Icon(painterResource(id = R.drawable.ic_favorite), contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            } else if (sort.contains("Rarity")) {
                                Icon(painterResource(id = R.drawable.cs_go_counter_strike), contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                            Spacer(Modifier.width(4.dp))
                            Text(
                                sort,
                                color = if (selected) Color.White else Color.Black,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(28.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = onClear) { Text("Temizle") }
                Button(
                    onClick = onApply,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) { Text("Uygula", fontWeight = FontWeight.Bold) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterSortSheetPreview() {
    FilterSortSheet(
        rarityOptions = listOf("Olağandışı", "Gizli", "Çok Gizli", "Askeri Sınıf", "Endüstri Sınıfı", "Sınırlı"),
        selectedRarities = setOf("Çok Gizli"),
        onRarityChange = {},
        collectionOptions = listOf("The Dust Collection", "The Mirage Collection", "The Inferno Collection"),
        selectedCollection = "The Dust Collection",
        onCollectionChange = {},
        priceRange = 0f..1000f,
        selectedPriceRange = 100f..800f,
        onPriceChange = {},
        sortOptions = listOf("A-Z", "Z-A", "Rarity ↑", "Rarity ↓"),
        selectedSort = "A-Z",
        onSortChange = {},
        onClear = {},
        onApply = {},
        onDismiss = {}
    )
}