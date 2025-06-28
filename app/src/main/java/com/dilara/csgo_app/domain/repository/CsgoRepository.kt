package com.dilara.csgo_app.domain.repository

import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.Sticker

interface CsgoRepository {
    suspend fun getSkins(language: String): List<Skin>
    suspend fun getAgents(language: String): List<Agent>
    suspend fun getStickers(language: String): List<Sticker>
    suspend fun getCrates(language: String): List<Crate>
} 