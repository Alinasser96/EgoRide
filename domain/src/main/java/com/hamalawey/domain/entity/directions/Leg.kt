package com.hamalawey.domain.entity.directions

data class Leg(
    val distance: Distance,
    val duration: Duration,
    val end_address: String,
    val end_location: StartLocation,
    val start_address: String,
    val start_location: StartLocation,
    val steps: List<Step>,
    val traffic_speed_entry: List<Any>,
    val via_waypoint: List<Any>
)