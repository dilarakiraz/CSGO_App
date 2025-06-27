package com.dilara.csgo_app.domain.model

data class Agent(
    val id: String,
    val name: String,
    val description: String,
    val rarity: Rarity,
    val collections: List<SkinCollection>,
    val team: Team,
    val marketHashName: String,
    val image: String
) 