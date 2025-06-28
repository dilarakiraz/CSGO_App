package com.dilara.csgo_app.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.dilara.csgo_app.ui.viewmodels.FavoriteItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object FavoritesPrefs {
    private const val PREFS_NAME = "favorites_prefs"
    private const val FAVORITES_KEY = "favorites_json"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val type = Types.newParameterizedType(List::class.java, FavoriteItem::class.java)
    private val adapter = moshi.adapter<List<FavoriteItem>>(type)

    fun saveFavorites(context: Context, favorites: List<FavoriteItem>) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = adapter.toJson(favorites)
        prefs.edit().putString(FAVORITES_KEY, json).apply()
    }

    fun loadFavorites(context: Context): List<FavoriteItem> {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(FAVORITES_KEY, null) ?: return emptyList()
        return adapter.fromJson(json) ?: emptyList()
    }
} 