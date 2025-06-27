package com.dilara.csgo_app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AgentDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val rarity: RarityDto?,
    val collections: List<CollectionDto>?,
    val team: TeamDto?,
    @Json(name = "market_hash_name") val marketHashName: String?,
    val image: String?
) 