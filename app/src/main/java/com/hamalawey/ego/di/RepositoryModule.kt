package com.hamalawey.brandz.di

import com.hamalawey.data.remote.EgoApiService
import com.hamalawey.data.repository.NavigationRepoImpl
import com.hamalawey.domain.repository.NavigationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideNavigationRepository(
        egoApiService: EgoApiService
    ): NavigationRepo =
        NavigationRepoImpl(egoApiService)
}