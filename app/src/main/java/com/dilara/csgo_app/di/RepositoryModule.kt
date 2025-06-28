package com.dilara.csgo_app.di

import com.dilara.csgo_app.data.repository.CsgoRepositoryImpl
import com.dilara.csgo_app.domain.repository.CsgoRepository
import com.dilara.csgo_app.ui.viewmodels.FavoritesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCsgoRepository(
        csgoRepositoryImpl: CsgoRepositoryImpl
    ): CsgoRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideFavoritesViewModel(): FavoritesViewModel {
        return FavoritesViewModel()
    }
}