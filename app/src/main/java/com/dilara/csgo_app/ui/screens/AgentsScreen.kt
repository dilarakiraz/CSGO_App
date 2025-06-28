package com.dilara.csgo_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.Team
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.components.EmptyScreen
import com.dilara.csgo_app.ui.components.ErrorView
import com.dilara.csgo_app.ui.components.LoadingView
import com.dilara.csgo_app.ui.components.ModernItemCard
import com.dilara.csgo_app.ui.components.SkinsScreenAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentsScreen(
    uiState: UiState<List<Agent>>,
    onRetry: () -> Unit,
    onAgentClick: (Agent) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Column {
        SkinsScreenAppBar(
            title = "Agents",
            onBackClick = onBackClick,
            onSearchClick = null
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
            when (uiState) {
                is UiState.Loading -> {
                    LoadingView()
                }

                is UiState.Success -> {
                    if (uiState.data.isEmpty()) {
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
                            items(uiState.data) { agent ->
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

                is UiState.Error -> {
                    ErrorView(
                        message = uiState.message,
                        onRetry = onRetry
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