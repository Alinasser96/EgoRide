package com.hamalawey.brandz.di

import com.hamalawey.domain.repository.NavigationRepo
import com.hamalawey.domain.usecase.GetDirectionsRemote
import com.hamalawey.domain.usecase.GetPlacesRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetPlacesUseCase(navigationRepo: NavigationRepo) = GetPlacesRemote(navigationRepo)

    @Provides
    fun provideGetDirectionsUseCase(navigationRepo: NavigationRepo) =
        GetDirectionsRemote(navigationRepo)

}