package com.hamalawey.data.repository

import com.hamalawey.data.remote.EgoApiService
import com.hamalawey.domain.entity.directions.DirectionsResponse
import com.hamalawey.domain.entity.places.PlacesResponse
import com.hamalawey.domain.repository.NavigationRepo

class NavigationRepoImpl (
    private val egoApiService: EgoApiService
    ) : NavigationRepo {
    override suspend fun getPlacesRemote(input: String): PlacesResponse {
        return egoApiService.getPlaces(input = input)
    }

    override suspend fun getDirectionsRemote(
        origin: String,
        destination: String
    ): DirectionsResponse {
        return egoApiService.getDirections(origin = origin, destination = destination)
    }
}