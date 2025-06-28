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

    override suspend fun getSkins(language: String): List<Skin> {
        return apiService.getSkins(language).map { it.toDomain() }
    }

    override suspend fun getAgents(language: String): List<Agent> {
        return apiService.getAgents(language).map { it.toDomain() }
    }

    override suspend fun getStickers(language: String): List<Sticker> {
        return apiService.getStickers(language).map { it.toDomain() }
    }

    override suspend fun getCrates(language: String): List<Crate> {
        return apiService.getCrates(language).map { it.toDomain() }
    }
} 