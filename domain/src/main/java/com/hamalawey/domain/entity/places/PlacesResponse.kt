package com.hamalawey.domain.entity.places

data class PlacesResponse(
    val predictions: List<Prediction>,
    val status: String
)