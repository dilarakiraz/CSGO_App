package com.dilara.csgo_app.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Skins : Screen("skins")
    object Agents : Screen("agents")
    object Stickers : Screen("stickers")
    object Crates : Screen("crates")
}