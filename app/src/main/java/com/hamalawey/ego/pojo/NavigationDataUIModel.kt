package com.hamalawey.ego.pojo

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.model.LatLng

data class NavigationDataUIModel(
    val points: List<MutableList<LatLng>>,
    val cameraUpdate: CameraUpdate
)