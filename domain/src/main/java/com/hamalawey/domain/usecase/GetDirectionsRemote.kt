package com.hamalawey.domain.usecase

import com.hamalawey.domain.repository.NavigationRepo

class GetDirectionsRemote (private val navigationRepo: NavigationRepo) {
    suspend fun invoke(origin: String, destination: String) =
        navigationRepo.getDirectionsRemote(origin = origin, destination = destination)
}