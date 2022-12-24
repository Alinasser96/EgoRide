package com.hamalawey.domain.entity.directions

data class Step(
    val distance: DistanceX,
    val duration: DurationX,
    val end_location: StartLocation,
    val html_instructions: String,
    val polyline: PolylineX,
    val start_location: StartLocation,
    val travel_mode: String
)