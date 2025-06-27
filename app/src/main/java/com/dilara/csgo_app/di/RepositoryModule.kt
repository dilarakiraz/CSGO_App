package com.dilara.csgo_app.di

import com.dilara.csgo_app.data.repository.CsgoRepositoryImpl
import com.dilara.csgo_app.domain.repository.CsgoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCsgoRepository(
        csgoRepositoryImpl: CsgoRepositoryImpl
    ): CsgoRepository
}