package com.dilara.csgo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dilara.csgo_app.ui.common.UiState
import com.dilara.csgo_app.ui.screens.AgentDetailScreen
import com.dilara.csgo_app.ui.screens.AgentsScreen
import com.dilara.csgo_app.ui.screens.CrateDetailScreen
import com.dilara.csgo_app.ui.screens.CratesScreen
import com.dilara.csgo_app.ui.screens.FavoritesScreen
import com.dilara.csgo_app.ui.screens.HomeScreen
import com.dilara.csgo_app.ui.screens.SkinDetailScreen
import com.dilara.csgo_app.ui.screens.SkinsScreen
import com.dilara.csgo_app.ui.screens.StickerDetailScreen
import com.dilara.csgo_app.ui.screens.StickersScreen
import com.dilara.csgo_app.ui.viewmodels.AgentsViewModel
import com.dilara.csgo_app.ui.viewmodels.CratesViewModel
import com.dilara.csgo_app.ui.viewmodels.SkinsViewModel
import com.dilara.csgo_app.ui.viewmodels.StickersViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
    val favoritesViewModel: com.dilara.csgo_app.ui.viewmodels.FavoritesViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    val context = LocalContext.current
    androidx.compose.runtime.LaunchedEffect(Unit) {
        favoritesViewModel.loadFavorites(context)
    }
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSkins = {
                    navController.navigate(Screen.Skins.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onNavigateToAgents = {
                    navController.navigate(Screen.Agents.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onNavigateToStickers = {
                    navController.navigate(Screen.Stickers.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onNavigateToCrates = {
                    navController.navigate(Screen.Crates.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Skins.route) {
            val viewModel: SkinsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            SkinsScreen(
                uiState = uiState,
                onRetry = { viewModel.loadSkins() },
                onSkinClick = { skin ->
                    navController.navigate(Screen.SkinDetail.createRoute(skin.id))
                },
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.SkinDetail.route,
            arguments = listOf(navArgument("skinId") { type = NavType.StringType })
        ) { backStackEntry ->
            val skinId = backStackEntry.arguments?.getString("skinId") ?: ""
            val viewModel: SkinsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            val skin = when (val state = uiState) {
                is UiState.Success -> state.data.find { it.id == skinId }
                else -> null
            }

            SkinDetailScreen(
                skin = skin,
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(Screen.Agents.route) {
            val viewModel: AgentsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            AgentsScreen(
                uiState = uiState,
                onRetry = { viewModel.loadAgents() },
                onAgentClick = { agent ->
                    navController.navigate(Screen.AgentDetail.createRoute(agent.id))
                },
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.AgentDetail.route,
            arguments = listOf(navArgument("agentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val agentId = backStackEntry.arguments?.getString("agentId") ?: ""
            val viewModel: AgentsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            val agent = when (val state = uiState) {
                is UiState.Success -> state.data.find { it.id == agentId }
                else -> null
            }

            AgentDetailScreen(
                agent = agent,
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(Screen.Stickers.route) {
            val viewModel: StickersViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            StickersScreen(
                uiState = uiState,
                onRetry = { viewModel.loadStickers() },
                onStickerClick = { sticker ->
                    navController.navigate(Screen.StickerDetail.createRoute(sticker.id))
                },
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.StickerDetail.route,
            arguments = listOf(navArgument("stickerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val stickerId = backStackEntry.arguments?.getString("stickerId") ?: ""
            val viewModel: StickersViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            val sticker = when (val state = uiState) {
                is UiState.Success -> state.data.find { it.id == stickerId }
                else -> null
            }

            StickerDetailScreen(
                sticker = sticker,
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(Screen.Crates.route) {
            val viewModel: CratesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            CratesScreen(
                uiState = uiState,
                onRetry = { viewModel.loadCrates() },
                onCrateClick = { crate ->
                    navController.navigate(Screen.CrateDetail.createRoute(crate.id))
                },
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.CrateDetail.route,
            arguments = listOf(navArgument("crateId") { type = NavType.StringType })
        ) { backStackEntry ->
            val crateId = backStackEntry.arguments?.getString("crateId") ?: ""
            val viewModel: CratesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            val crate = when (val state = uiState) {
                is UiState.Success -> state.data.find { it.id == crateId }
                else -> null
            }

            CrateDetailScreen(
                crate = crate,
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onItemClick = { type, id ->
                    when (type) {
                        "skin" -> navController.navigate(Screen.SkinDetail.createRoute(id))
                        "agent" -> navController.navigate(Screen.AgentDetail.createRoute(id))
                        "sticker" -> navController.navigate(Screen.StickerDetail.createRoute(id))
                        "crate" -> navController.navigate(Screen.CrateDetail.createRoute(id))
                    }
                },
                favoritesViewModel = favoritesViewModel
            )
        }
    }
} 