package com.dilara.csgo_app.data.api

import com.dilara.csgo_app.data.model.AgentDto
import com.dilara.csgo_app.data.model.CrateDto
import com.dilara.csgo_app.data.model.SkinDto
import com.dilara.csgo_app.data.model.StickerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CsgoApiService {

    @GET("public/api/{language}/skins.json")
    suspend fun getSkins(@Path("language") language: String): List<SkinDto>

    @GET("public/api/{language}/agents.json")
    suspend fun getAgents(@Path("language") language: String): List<AgentDto>

    @GET("public/api/{language}/stickers.json")
    suspend fun getStickers(@Path("language") language: String): List<StickerDto>

    @GET("public/api/{language}/crates.json")
    suspend fun getCrates(@Path("language") language: String): List<CrateDto>
} 