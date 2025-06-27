package com.dilara.csgo_app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SkinDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val weapon: WeaponDto?,
    val category: CategoryDto?,
    val pattern: PatternDto?,
    @Json(name = "min_float") val minFloat: Double?,
    @Json(name = "max_float") val maxFloat: Double?,
    val rarity: RarityDto?,
    val stattrak: Boolean?,
    val souvenir: Boolean?,
    @Json(name = "paint_index") val paintIndex: String?,
    val wears: List<WearDto>?,
    val collections: List<CollectionDto>?,
    val crates: List<SimpleCrateDto>?,
    val team: TeamDto?,
    @Json(name = "legacy_model") val legacyModel: Boolean?,
    val image: String?
)

@JsonClass(generateAdapter = true)
data class WeaponDto(
    val id: String?,
    @Json(name = "weapon_id") val weaponId: Int?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class CategoryDto(
    val id: String?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class PatternDto(
    val id: String?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class RarityDto(
    val id: String?,
    val name: String?,
    val color: String?
)

@JsonClass(generateAdapter = true)
data class WearDto(
    val id: String?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class CollectionDto(
    val id: String?,
    val name: String?,
    val image: String?
)

@JsonClass(generateAdapter = true)
data class SimpleCrateDto(
    val id: String?,
    val name: String?,
    val image: String?
)

@JsonClass(generateAdapter = true)
data class TeamDto(
    val id: String?,
    val name: String?
) 