package com.dilara.csgo_app.domain.model

data class Skin(
    val id: String,
    val name: String,
    val description: String,
    val weapon: Weapon,
    val category: Category,
    val pattern: Pattern,
    val minFloat: Double,
    val maxFloat: Double,
    val rarity: Rarity,
    val stattrak: Boolean,
    val souvenir: Boolean,
    val paintIndex: String,
    val wears: List<Wear>,
    val collections: List<SkinCollection>,
    val crates: List<SimpleCrate>,
    val team: Team,
    val legacyModel: Boolean,
    val image: String
)

data class Weapon(
    val id: String,
    val weaponId: Int,
    val name: String
)

data class Category(
    val id: String,
    val name: String
)

data class Pattern(
    val id: String,
    val name: String
)

data class Rarity(
    val id: String,
    val name: String,
    val color: String
)

data class Wear(
    val id: String,
    val name: String
)

data class SkinCollection(
    val id: String,
    val name: String,
    val image: String
)

data class SimpleCrate(
    val id: String,
    val name: String,
    val image: String
)

data class Team(
    val id: String,
    val name: String
) 