package com.dilara.csgo_app.domain.repository

import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.Sticker

interface CsgoRepository {
    suspend fun getSkins(): List<Skin>
    suspend fun getAgents(): List<Agent>
    suspend fun getStickers(): List<Sticker>
    suspend fun getCrates(): List<Crate>
} 