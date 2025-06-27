package com.dilara.csgo_app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StickerDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val rarity: RarityDto?,
    val crates: List<CrateDto>?,
    @Json(name = "tournament_event") val tournamentEvent: String?,
    @Json(name = "tournament_team") val tournamentTeam: String?,
    val type: String?,
    @Json(name = "market_hash_name") val marketHashName: String?,
    val effect: String?,
    val image: String?
) 