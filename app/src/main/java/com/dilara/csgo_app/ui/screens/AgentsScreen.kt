package com.dilara.csgo_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.Team
import com.dilara.csgo_app.ui.common.SortOption
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.FilterSortSheet
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar
import com.dilara.csgo_app.ui.viewmodels.AgentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentsScreen(
    uiState: UiState<List<Agent>>,
    onRetry: () -> Unit,
    onAgentClick: (Agent) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: AgentsViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val filterSortState by viewModel.filterSortState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val showSearchSheet = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val coroutineScope = rememberCoroutineScope()
    val agents = (uiState as? UiState.Success)?.data ?: emptyList()
    val filteredSearchAgents = agents.filter {
        it.name.contains(searchQuery.value, ignoreCase = true)
    }
    Box(Modifier.fillMaxSize()) {
        Column {
            SkinsScreenAppBar(
                title = "Agents",
                onBackClick = onBackClick,
                onSearchClick = { showSearchSheet.value = true },
                onFilterClick = { showDialog.value = true }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            ) {
                val filteredAgents = (uiState as? UiState.Success)?.data
                    ?.filter { agent ->
                        (filterSortState.selectedRarities.isEmpty() || agent.rarity.name in filterSortState.selectedRarities) &&
                                (filterSortState.selectedTeam == null || agent.team.name == filterSortState.selectedTeam)
                    }
                    ?.sortedWith(
                        when (filterSortState.sortOption) {
                            SortOption.AZ -> compareBy { it.name }
                            SortOption.ZA -> compareByDescending { it.name }
                            SortOption.RARITY_ASC -> compareBy { it.rarity.name }
                            SortOption.RARITY_DESC -> compareByDescending { it.rarity.name }
                        }
                    ) ?: emptyList()

                when (uiState) {
                    is UiState.Loading -> LoadingView()
                    is UiState.Success -> {
                        if (filteredAgents.isEmpty()) {
                            EmptyScreen(
                                icon = Icons.Default.Person,
                                title = "No Agents Found",
                                description = "No character agents are available at the moment.",
                                onActionClick = onRetry,
                                actionText = "Retry"
                            )
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(filteredAgents) { agent ->
                                    ModernItemCard(
                                        title = agent.name,
                                        subtitle = agent.team.name,
                                        imageUrl = agent.image,
                                        rarityName = agent.rarity.name,
                                        rarityColor = agent.rarity.color,
                                        onClick = { onAgentClick(agent) }
                                    )
                                }
                            }
                        }
                    }

                    is UiState.Error -> ErrorView(message = uiState.message, onRetry = onRetry)
                }
            }
        }
        if (showSearchSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showSearchSheet.value = false
                    searchQuery.value = ""
                },
                sheetState = sheetState,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .padding(bottom = 12.dp)
                            .width(48.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.Gray.copy(alpha = 0.25f))
                    )
                    TextField(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        placeholder = { Text("Ajan ara...", color = Color.Gray) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Agents",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (filteredSearchAgents.isEmpty()) {
                        Text(
                            text = "Sonuç bulunamadı.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 32.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(filteredSearchAgents) { agent ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            showSearchSheet.value = false
                                            searchQuery.value = ""
                                            onAgentClick(agent)
                                        },
                                    shape = RoundedCornerShape(14.dp),
                                    tonalElevation = 2.dp,
                                    color = MaterialTheme.colorScheme.surface
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(12.dp)
                                    ) {
                                        AsyncImage(
                                            model = agent.image,
                                            contentDescription = agent.name,
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(RoundedCornerShape(10.dp)),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                        Spacer(Modifier.width(16.dp))
                                        Column {
                                            Text(agent.name, style = MaterialTheme.typography.titleMedium)
                                            Text(agent.team.name, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (showDialog.value) {
            Box(
                Modifier
                    .fillMaxSize()
                    .zIndex(2f)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.45f))
                        .clickable(onClick = { showDialog.value = false })
                ) {}
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.95f)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(Modifier.padding(top = 8.dp, end = 8.dp)) {
                        IconButton(
                            onClick = { showDialog.value = false },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Kapat")
                        }
                    }
                    FilterSortSheet(
                        rarityOptions = (uiState as? UiState.Success)?.data?.map { it.rarity.name }?.distinct() ?: emptyList(),
                        selectedRarities = filterSortState.selectedRarities,
                        onRarityChange = { viewModel.updateRarity(it) },
                        collectionOptions = (uiState as? UiState.Success)?.data?.map { it.team.name }?.distinct() ?: emptyList(),
                        selectedCollection = filterSortState.selectedTeam,
                        onCollectionChange = { viewModel.updateTeam(it) },
                        priceRange = 0f..0f,
                        selectedPriceRange = 0f..0f,
                        onPriceChange = {},
                        sortOptions = listOf("A-Z", "Z-A", "Rarity ↑", "Rarity ↓"),
                        selectedSort = when (filterSortState.sortOption) {
                            SortOption.AZ -> "A-Z"
                            SortOption.ZA -> "Z-A"
                            SortOption.RARITY_ASC -> "Rarity ↑"
                            SortOption.RARITY_DESC -> "Rarity ↓"
                        },
                        onSortChange = {
                            viewModel.updateSortOption(
                                when (it) {
                                    "A-Z" -> SortOption.AZ
                                    "Z-A" -> SortOption.ZA
                                    "Rarity ↑" -> SortOption.RARITY_ASC
                                    "Rarity ↓" -> SortOption.RARITY_DESC
                                    else -> SortOption.AZ
                                }
                            )
                        },
                        onClear = { viewModel.clearFilters() },
                        onApply = { showDialog.value = false },
                        onDismiss = { showDialog.value = false },
                        showPriceFilter = false
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgentsScreenPreview() {
    val sampleAgents = listOf(
        Agent(
            id = "1",
            name = "Special Agent Ava",
            description = "Elite agent from FBI",
            rarity = Rarity("master", "Master Agent", "#FFD700"),
            collections = emptyList(),
            team = Team("ct", "CT"),
            marketHashName = "Special Agent Ava | FBI | Master Agent",
            image = "https://picsum.photos/300/200?random=3"
        ),
        Agent(
            id = "2",
            name = "Operator",
            description = "Tactical operator",
            rarity = Rarity("distinguished", "Distinguished", "#4B69FF"),
            collections = emptyList(),
            team = Team("t", "T"),
            marketHashName = "Operator | Tactical | Distinguished",
            image = "https://picsum.photos/300/200?random=4"
        )
    )
    AgentsScreen(
        uiState = UiState.Success(sampleAgents),
        onRetry = {},
        onAgentClick = {},
        onBackClick = {},
        onNavigateToHome = {}
    )
} 