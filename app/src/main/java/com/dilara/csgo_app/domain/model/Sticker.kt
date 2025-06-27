package com.dilara.csgo_app.domain.model

data class Sticker(
    val id: String,
    val name: String,
    val description: String,
    val rarity: Rarity,
    val crates: List<SimpleCrate>,
    val tournamentEvent: String?,
    val tournamentTeam: String?,
    val type: String?,
    val marketHashName: String,
    val effect: String?,
    val image: String
) 