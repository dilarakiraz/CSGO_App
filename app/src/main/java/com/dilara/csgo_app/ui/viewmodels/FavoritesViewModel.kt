package com.dilara.csgo_app.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.model.Sticker
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.data.source.local.FavoritesPrefs
import javax.inject.Inject
import javax.inject.Singleton

data class FavoriteItem(
    val type: String, // "skin", "agent", "sticker", "crate"
    val id: String,
    val name: String,
    val subtitle: String,
    val imageUrl: String,
    val rarityName: String,
    val rarityColor: String
)

@Singleton
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val _favoriteItems = mutableStateOf<List<FavoriteItem>>(emptyList())
    val favoriteItems: State<List<FavoriteItem>> = _favoriteItems
    val favoriteIds: List<String> get() = _favoriteItems.value.map { it.id }

    fun isFavorite(id: String): Boolean = _favoriteItems.value.any { it.id == id }

    fun loadFavorites(context: Context) {
        _favoriteItems.value = FavoritesPrefs.loadFavorites(context)
    }

    fun toggleFavorite(context: Context, id: String) {
        val currentList = _favoriteItems.value.toMutableList()
        val existingItem = currentList.find { it.id == id }
        if (existingItem != null) {
            currentList.remove(existingItem)
            _favoriteItems.value = currentList
            saveFavorites(context)
        }
    }

    fun addFavoriteSkin(context: Context, skin: Skin) {
        if (!isFavorite(skin.id)) {
            val currentList = _favoriteItems.value.toMutableList()
            currentList.add(
                FavoriteItem(
                    type = "skin",
                    id = skin.id,
                    name = skin.name,
                    subtitle = skin.weapon.name,
                    imageUrl = skin.image,
                    rarityName = skin.rarity.name,
                    rarityColor = getRarityColor(skin.rarity.name)
                )
            )
            _favoriteItems.value = currentList
            saveFavorites(context)
        }
    }

    fun addFavoriteAgent(context: Context, agent: Agent) {
        if (!isFavorite(agent.id)) {
            val currentList = _favoriteItems.value.toMutableList()
            currentList.add(
                FavoriteItem(
                    type = "agent",
                    id = agent.id,
                    name = agent.name,
                    subtitle = agent.team.name,
                    imageUrl = agent.image,
                    rarityName = agent.rarity.name,
                    rarityColor = getRarityColor(agent.rarity.name)
                )
            )
            _favoriteItems.value = currentList
            saveFavorites(context)
        }
    }

    fun addFavoriteSticker(context: Context, sticker: Sticker) {
        if (!isFavorite(sticker.id)) {
            val currentList = _favoriteItems.value.toMutableList()
            currentList.add(
                FavoriteItem(
                    type = "sticker",
                    id = sticker.id,
                    name = sticker.name,
                    subtitle = "Sticker",
                    imageUrl = sticker.image,
                    rarityName = sticker.rarity.name,
                    rarityColor = getRarityColor(sticker.rarity.name)
                )
            )
            _favoriteItems.value = currentList
            saveFavorites(context)
        }
    }

    fun addFavoriteCrate(context: Context, crate: Crate) {
        if (!isFavorite(crate.id)) {
            val currentList = _favoriteItems.value.toMutableList()
            currentList.add(
                FavoriteItem(
                    type = "crate",
                    id = crate.id,
                    name = crate.name,
                    subtitle = "Weapon Case",
                    imageUrl = crate.image,
                    rarityName = "Case",
                    rarityColor = "#FFD700"
                )
            )
            _favoriteItems.value = currentList
            saveFavorites(context)
        }
    }

    private fun saveFavorites(context: Context) {
        FavoritesPrefs.saveFavorites(context, _favoriteItems.value)
    }

    private fun getRarityColor(rarityName: String): String {
        return when (rarityName.lowercase()) {
            "consumer grade", "white" -> "#B0C3D9"
            "industrial grade", "light blue" -> "#5E98D9"
            "mil-spec grade", "blue" -> "#4B69FF"
            "restricted", "purple" -> "#8847FF"
            "classified", "pink" -> "#D32CE6"
            "covert", "red" -> "#EB4B4B"
            "contraband", "master agent" -> "#FFD700"
            else -> "#FFD700"
        }
    }
} 