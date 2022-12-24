package com.hamalawey.domain.repository

import com.hamalawey.domain.entity.directions.DirectionsResponse
import com.hamalawey.domain.entity.places.PlacesResponse

interface NavigationRepo {
    suspend fun getPlacesRemote(input: String) : PlacesResponse
    suspend fun getDirectionsRemote(origin: String, destination: String) : DirectionsResponse
}