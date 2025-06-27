package com.dilara.csgo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dilara.csgo_app.ui.screens.*
import com.dilara.csgo_app.ui.viewmodels.*

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSkins = { navController.navigate(Screen.Skins.route) },
                onNavigateToAgents = { navController.navigate(Screen.Agents.route) },
                onNavigateToStickers = { navController.navigate(Screen.Stickers.route) },
                onNavigateToCrates = { navController.navigate(Screen.Crates.route) }
            )
        }
        
        composable(Screen.Skins.route) {
            val viewModel: SkinsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            SkinsScreen(
                uiState = uiState,
                onRetry = { viewModel.loadSkins() },
                onSkinClick = { /* TODO: Navigate to skin detail */ },
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Agents.route) {
            val viewModel: AgentsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            AgentsScreen(
                uiState = uiState,
                onRetry = { viewModel.loadAgents() },
                onAgentClick = { /* TODO: Navigate to agent detail */ },
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Stickers.route) {
            val viewModel: StickersViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            StickersScreen(
                uiState = uiState,
                onRetry = { viewModel.loadStickers() },
                onStickerClick = { /* TODO: Navigate to sticker detail */ },
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Crates.route) {
            val viewModel: CratesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            CratesScreen(
                uiState = uiState,
                onRetry = { viewModel.loadCrates() },
                onCrateClick = { /* TODO: Navigate to crate detail */ },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
} 