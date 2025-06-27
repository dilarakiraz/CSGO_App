package com.dilara.csgo_app.data.repository

import com.dilara.csgo_app.data.source.remote.MainService
import com.dilara.csgo_app.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService,
) : MainRepository