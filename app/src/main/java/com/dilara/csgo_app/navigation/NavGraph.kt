package com.dilara.csgo_app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.dilara.csgo_app.ui.theme.CsgoGradientEnd
import com.dilara.csgo_app.ui.theme.CsgoGradientStart
import com.dilara.csgo_app.ui.viewmodels.AgentsViewModel
import com.dilara.csgo_app.ui.viewmodels.CratesViewModel
import com.dilara.csgo_app.ui.viewmodels.LanguageViewModel
import com.dilara.csgo_app.ui.viewmodels.SkinsViewModel
import com.dilara.csgo_app.ui.viewmodels.StickersViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController
) {
    val favoritesViewModel: com.dilara.csgo_app.ui.viewmodels.FavoritesViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()
    val selectedLanguage by languageViewModel.selectedLanguage
    val context = androidx.compose.ui.platform.LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLanguageDialog by remember { mutableStateOf(false) }
    var pendingLanguage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        favoritesViewModel.loadFavorites(context)
    }

    if (showLanguageDialog && pendingLanguage != null) {
        Dialog(onDismissRequest = { showLanguageDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Dil Değiştir",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${if (pendingLanguage == "tr") "Türkçe" else "English"} diline geçmek istediğinizden emin misiniz?",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showLanguageDialog = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("İptal")
                        }
                        Button(
                            onClick = {
                                languageViewModel.selectedLanguage.value = pendingLanguage!!
                                showLanguageDialog = false
                                pendingLanguage = null
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Onayla")
                        }
                    }
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(320.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(CsgoGradientStart, CsgoGradientEnd)
                            )
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        Text(
                            text = "CS:GO Arsenal",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Counter-Strike 2",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Home
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Ana Sayfa") },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(0) { inclusive = true }
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Favorites
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                        label = { Text("Favoriler") },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.Favorites.route) {
                                popUpTo(0) { inclusive = true }
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )

                    Text(
                        text = "Dil Seçimi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (selectedLanguage == "tr")
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        else Color.Transparent
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedLanguage == "tr",
                                    onClick = {
                                        pendingLanguage = "tr"
                                        showLanguageDialog = true
                                    }
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Türkçe",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Turkish",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (selectedLanguage == "en")
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        else Color.Transparent
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedLanguage == "en",
                                    onClick = {
                                        pendingLanguage = "en"
                                        showLanguageDialog = true
                                    }
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "English",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "İngilizce",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Aktif Dil: ${if (selectedLanguage == "tr") "Türkçe" else "English"}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    ) {
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
                    },
                    onDrawerClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            }

            composable(Screen.Skins.route) {
                val viewModel: SkinsViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadSkins(selectedLanguage)
                }

                SkinsScreen(
                    uiState = uiState,
                    onRetry = { viewModel.loadSkins(selectedLanguage) },
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

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadSkins(selectedLanguage)
                }

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

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadAgents(selectedLanguage)
                }

                AgentsScreen(
                    uiState = uiState,
                    onRetry = { viewModel.loadAgents(selectedLanguage) },
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

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadAgents(selectedLanguage)
                }

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

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadStickers(selectedLanguage)
                }

                StickersScreen(
                    uiState = uiState,
                    onRetry = { viewModel.loadStickers(selectedLanguage) },
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

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadStickers(selectedLanguage)
                }

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

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadCrates(selectedLanguage)
                }

                CratesScreen(
                    uiState = uiState,
                    onRetry = { viewModel.loadCrates(selectedLanguage) },
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

                LaunchedEffect(selectedLanguage) {
                    viewModel.loadCrates(selectedLanguage)
                }

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
                    onBackClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
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
} 