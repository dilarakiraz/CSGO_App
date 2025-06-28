package com.dilara.csgo_app.data.repository

import com.dilara.csgo_app.data.api.CsgoApiService
import com.dilara.csgo_app.data.mapper.toDomain
import com.dilara.csgo_app.domain.model.Agent
import com.dilara.csgo_app.domain.model.Crate
import com.dilara.csgo_app.domain.model.Skin
import com.dilara.csgo_app.domain.model.Sticker
import com.dilara.csgo_app.domain.repository.CsgoRepository
import javax.inject.Inject

class CsgoRepositoryImpl @Inject constructor(
    private val apiService: CsgoApiService
) : CsgoRepository {

    override suspend fun getSkins(): List<Skin> {
        return apiService.getSkins().map { it.toDomain() }
    }

    override suspend fun getAgents(): List<Agent> {
        return apiService.getAgents().map { it.toDomain() }
    }

    override suspend fun getStickers(): List<Sticker> {
        return apiService.getStickers().map { it.toDomain() }
    }

    override suspend fun getCrates(): List<Crate> {
        return apiService.getCrates().map { it.toDomain() }
    }
} 