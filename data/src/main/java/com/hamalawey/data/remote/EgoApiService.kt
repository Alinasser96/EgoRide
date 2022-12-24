package com.hamalawey.data.remote

import com.hamalawey.data.BuildConfig
import com.hamalawey.domain.entity.directions.DirectionsResponse
import com.hamalawey.domain.entity.places.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EgoApiService{
    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ) : DirectionsResponse

    @GET("place/autocomplete/json")
    suspend fun getPlaces(
        @Query("input") input: String,
        @Query("types") types: String = "geocode",
        @Query("key") key: String = BuildConfig.API_KEY
    ) : PlacesResponse
}