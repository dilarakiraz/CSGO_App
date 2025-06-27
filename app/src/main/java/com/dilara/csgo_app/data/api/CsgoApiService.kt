package com.dilara.csgo_app.data.api

import com.dilara.csgo_app.data.model.AgentDto
import com.dilara.csgo_app.data.model.CrateDto
import com.dilara.csgo_app.data.model.SkinDto
import com.dilara.csgo_app.data.model.StickerDto
import retrofit2.http.GET

interface CsgoApiService {
    
    @GET("public/api/en/skins.json")
    suspend fun getSkins(): List<SkinDto>
    
    @GET("public/api/en/agents.json")
    suspend fun getAgents(): List<AgentDto>
    
    @GET("public/api/en/stickers.json")
    suspend fun getStickers(): List<StickerDto>
    
    @GET("public/api/en/crates.json")
    suspend fun getCrates(): List<CrateDto>
} 