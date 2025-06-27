package com.dilara.csgo_app.domain.model

data class Crate(
    val id: String,
    val name: String,
    val description: String?,
    val type: String,
    val firstSaleDate: String?,
    val contains: List<CrateItem>,
    val containsRare: List<CrateItem>,
    val marketHashName: String,
    val rental: Boolean?,
    val image: String,
    val modelPlayer: String?,
    val lootList: LootList?
)

data class CrateItem(
    val id: String,
    val name: String,
    val rarity: Rarity,
    val paintIndex: String?,
    val phase: String?,
    val image: String
)

data class LootList(
    val name: String,
    val footer: String,
    val image: String
) 