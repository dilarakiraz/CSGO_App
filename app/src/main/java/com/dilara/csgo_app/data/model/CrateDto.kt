package com.dilara.csgo_app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrateDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val type: String?,
    @Json(name = "first_sale_date") val firstSaleDate: String?,
    val contains: List<CrateItemDto>?,
    @Json(name = "contains_rare") val containsRare: List<CrateItemDto>?,
    @Json(name = "market_hash_name") val marketHashName: String?,
    val rental: Boolean?,
    val image: String?,
    @Json(name = "model_player") val modelPlayer: String?,
    @Json(name = "loot_list") val lootList: LootListDto?
)

@JsonClass(generateAdapter = true)
data class CrateItemDto(
    val id: String?,
    val name: String?,
    val rarity: RarityDto?,
    @Json(name = "paint_index") val paintIndex: String?,
    val phase: String?,
    val image: String?
)

@JsonClass(generateAdapter = true)
data class LootListDto(
    val name: String?,
    val footer: String?,
    val image: String?
) 