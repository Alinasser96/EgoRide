package com.hamalawey.ego

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import com.hamalawey.domain.entity.places.PlacesResponse
import com.hamalawey.domain.usecase.GetDirectionsRemote
import com.hamalawey.domain.usecase.GetPlacesRemote
import com.hamalawey.ego.pojo.NavigationDataUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EgoViewModel @Inject constructor(
    private val getPlacesRemote: GetPlacesRemote,
    private val getDirectionsRemote: GetDirectionsRemote,
) : ViewModel(){
    private val _pickup: MutableStateFlow<PlacesResponse?> = MutableStateFlow(null)
    val pickup: StateFlow<PlacesResponse?> = _pickup

    private val _dropOff: MutableStateFlow<PlacesResponse?> = MutableStateFlow(null)
    val dropOff: StateFlow<PlacesResponse?> = _dropOff

    private val _directions: MutableStateFlow<NavigationDataUIModel?> = MutableStateFlow(null)
    val directions: StateFlow<NavigationDataUIModel?> = _directions

    fun getPickup(input: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val places = getPlacesRemote.invoke(input)
                _pickup.value = places
            } catch (e: Exception){
                Log.d("Error",e.message.toString())
            }
        }
    }

    fun getDropOff(input: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val places = getPlacesRemote.invoke(input)
                _dropOff.value = places
            } catch (e: Exception){
                Log.d("Error",e.message.toString())
            }
        }
    }

    fun getDirections(origin: String, dest: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val directions = getDirectionsRemote.invoke("place_id:" + origin, "place_id:" + dest)

                val points = directions.routes[0].legs[0].steps.map {
                    PolyUtil.decode(it.polyline.points)
                }
                val builder = LatLngBounds.Builder()
                builder.include(LatLng(directions.routes[0].bounds.northeast.lat, directions.routes[0].bounds.northeast.lng))
                builder.include(LatLng(directions.routes[0].bounds.southwest.lat, directions.routes[0].bounds.southwest.lng))
                val bounds = builder.build()

                val cu = CameraUpdateFactory.newLatLngBounds(bounds, 100)

                _directions.value = NavigationDataUIModel(points, cu)

            } catch (e: Exception){
                Log.d("Error",e.message.toString())
            }
        }
    }
}