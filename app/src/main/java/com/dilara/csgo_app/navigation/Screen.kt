package com.dilara.csgo_app.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Skins : Screen("skins")
    object SkinDetail : Screen("skin_detail/{skinId}") {
        fun createRoute(skinId: String) = "skin_detail/$skinId"
    }

    object Agents : Screen("agents")
    object AgentDetail : Screen("agent_detail/{agentId}") {
        fun createRoute(agentId: String) = "agent_detail/$agentId"
    }

    object Stickers : Screen("stickers")
    object StickerDetail : Screen("sticker_detail/{stickerId}") {
        fun createRoute(stickerId: String) = "sticker_detail/$stickerId"
    }

    object Crates : Screen("crates")
    object CrateDetail : Screen("crate_detail/{crateId}") {
        fun createRoute(crateId: String) = "crate_detail/$crateId"
    }

    object Favorites : Screen("favorites")
}