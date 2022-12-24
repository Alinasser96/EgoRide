package com.hamalawey.domain.usecase

import com.hamalawey.domain.repository.NavigationRepo

class GetPlacesRemote(private val navigationRepo: NavigationRepo) {
    suspend fun invoke(input: String) = navigationRepo.getPlacesRemote(input = input)
}