package com.dilara.csgo_app.data.mapper

import com.dilara.csgo_app.data.model.AgentDto
import com.dilara.csgo_app.data.model.CategoryDto
import com.dilara.csgo_app.data.model.CollectionDto
import com.dilara.csgo_app.data.model.CrateDto
import com.dilara.csgo_app.data.model.CrateItemDto
import com.dilara.csgo_app.data.model.LootListDto
import com.dilara.csgo_app.data.model.PatternDto
import com.dilara.csgo_app.data.model.RarityDto
import com.dilara.csgo_app.data.model.SimpleCrateDto
import com.dilara.csgo_app.data.model.SkinDto
import com.dilara.csgo_app.data.model.StickerDto
import com.dilara.csgo_app.data.model.TeamDto
import com.dilara.csgo_app.data.model.WeaponDto
import com.dilara.csgo_app.data.model.WearDto
import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.model.Category
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.domain.model.CrateItem
import com.dilara.csgo_app.domain.model.LootList
import com.dilara.csgo_app.domain.model.Pattern
import com.dilara.csgo_app.domain.model.Rarity
import com.dilara.csgo_app.domain.model.SimpleCrate
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.SkinCollection
import com.dilara.csgo_app.domain.model.Sticker
import com.dilara.csgo_app.domain.model.Team
import com.dilara.csgo_app.domain.model.Weapon
import com.dilara.csgo_app.domain.model.Wear

// Common mappers
fun RarityDto.toDomain(): Rarity {
    return Rarity(
        id = id ?: "",
        name = name ?: "",
        color = color ?: "#808080"
    )
}

// Skin mappers
fun SkinDto.toDomain(): Skin {
    return Skin(
        id = id ?: "",
        name = name ?: "",
        description = description ?: "",
        weapon = weapon?.toDomain() ?: Weapon("", 0, ""),
        category = category?.toDomain() ?: Category("", ""),
        pattern = pattern?.toDomain() ?: Pattern("", ""),
        minFloat = minFloat ?: 0.0,
        maxFloat = maxFloat ?: 1.0,
        rarity = rarity?.toDomain() ?: Rarity("", "", "#808080"),
        stattrak = stattrak ?: false,
        souvenir = souvenir ?: false,
        paintIndex = paintIndex ?: "",
        wears = wears?.map { it.toDomain() } ?: emptyList(),
        collections = collections?.map { it.toDomain() } ?: emptyList(),
        crates = crates?.map { it.toSimpleCrate() } ?: emptyList(),
        team = team?.toDomain() ?: Team("", ""),
        legacyModel = legacyModel ?: false,
        image = image ?: ""
    )
}

fun WeaponDto.toDomain(): Weapon {
    return Weapon(
        id = id ?: "",
        weaponId = weaponId ?: 0,
        name = name ?: ""
    )
}

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id ?: "",
        name = name ?: ""
    )
}

fun PatternDto.toDomain(): Pattern {
    return Pattern(
        id = id ?: "",
        name = name ?: ""
    )
}

fun WearDto.toDomain(): Wear {
    return Wear(
        id = id ?: "",
        name = name ?: ""
    )
}

fun CollectionDto.toDomain(): SkinCollection {
    return SkinCollection(
        id = id ?: "",
        name = name ?: "",
        image = image ?: ""
    )
}

fun TeamDto.toDomain(): Team {
    return Team(
        id = id ?: "",
        name = name ?: ""
    )
}

// Simple Crate conversion for Skin domain model
fun SimpleCrateDto.toSimpleCrate(): SimpleCrate {
    return SimpleCrate(
        id = id ?: "",
        name = name ?: "",
        image = image ?: ""
    )
}

// CrateDto to SimpleCrate conversion for Sticker domain model
fun CrateDto.toSimpleCrate(): SimpleCrate {
    return SimpleCrate(
        id = id ?: "",
        name = name ?: "",
        image = image ?: ""
    )
}

// Agent mappers
fun AgentDto.toDomain(): Agent {
    return Agent(
        id = id ?: "",
        name = name ?: "",
        description = description ?: "",
        rarity = rarity?.toDomain() ?: Rarity("", "", "#808080"),
        collections = collections?.map { it.toDomain() } ?: emptyList(),
        team = team?.toDomain() ?: Team("", ""),
        marketHashName = marketHashName ?: "",
        image = image ?: ""
    )
}

// Sticker mappers
fun StickerDto.toDomain(): Sticker {
    return Sticker(
        id = id ?: "",
        name = name ?: "",
        description = description ?: "",
        rarity = rarity?.toDomain() ?: Rarity("", "", "#808080"),
        crates = crates?.map { it.toSimpleCrate() } ?: emptyList(),
        tournamentEvent = tournamentEvent ?: "",
        tournamentTeam = tournamentTeam ?: "",
        type = type ?: "",
        marketHashName = marketHashName ?: "",
        effect = effect ?: "",
        image = image ?: ""
    )
}

// Crate mappers
fun CrateDto.toDomain(): Crate {
    return Crate(
        id = id ?: "",
        name = name ?: "",
        description = description ?: "",
        type = type ?: "",
        firstSaleDate = firstSaleDate ?: "",
        contains = contains?.map { it.toDomain() } ?: emptyList(),
        containsRare = containsRare?.map { it.toDomain() } ?: emptyList(),
        marketHashName = marketHashName ?: "",
        rental = rental ?: false,
        image = image ?: "",
        modelPlayer = modelPlayer ?: "",
        lootList = lootList?.toDomain()
    )
}

fun CrateItemDto.toDomain(): CrateItem {
    return CrateItem(
        id = id ?: "",
        name = name ?: "",
        rarity = rarity?.toDomain() ?: Rarity("", "", "#808080"),
        paintIndex = paintIndex ?: "",
        phase = phase ?: "",
        image = image ?: ""
    )
}

fun LootListDto.toDomain(): LootList {
    return LootList(
        name = name ?: "",
        footer = footer ?: "",
        image = image ?: ""
    )
} 