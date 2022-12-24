package com.hamalawey.ego

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.PolylineOptions
import com.hamalawey.domain.entity.places.Prediction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: EgoViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var pickupAdapter: ArrayAdapter<String>
    private lateinit var dropOffAdapter: ArrayAdapter<String>
    private lateinit var pickupPredictions: List<Prediction>
    private lateinit var dropOffPredictions: List<Prediction>
    private lateinit var origin: String
    private lateinit var destination: String
    private lateinit var pickupTV: AutoCompleteTextView
    private lateinit var dropOffTV: AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pickupTV = findViewById(R.id.pickup)
        dropOffTV = findViewById(R.id.dropOff)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setUpAdapters()

        setupClickListener()

        lifecycleScope.launch {
            viewModel.pickup.collect {
                it?.let {
                    pickupPredictions = it.predictions
                    pickupAdapter.clear()

                    it.predictions.forEach {
                        pickupAdapter.add(it.description)
                    }
                    pickupAdapter.filter.filter(pickupTV.text, null)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.dropOff.collect {
                it?.let {
                    dropOffPredictions = it.predictions
                    dropOffAdapter.clear()

                    it.predictions.forEach {
                        dropOffAdapter.add(it.description)
                    }
                    dropOffAdapter.filter.filter(dropOffTV.text, null)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.directions.collect {
                it?.let {
                    map.clear()

                    it.points.forEach {
                        map.addPolyline(PolylineOptions().addAll(it).color(Color.BLACK))
                    }
                    map.animateCamera(it.cameraUpdate)
                }
            }
        }
    }

    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    private fun setupClickListener(){
        pickupTV.setOnItemClickListener { _, _, i, _ ->
            origin = pickupPredictions[i].place_id
            if (this::destination.isInitialized) {
                viewModel.getDirections(origin, destination)
            }
            hideKeyboard()
        }
        dropOffTV.setOnItemClickListener { _, _, i, _ ->
            destination = dropOffPredictions[i].place_id
            if (this::origin.isInitialized) {
                viewModel.getDirections(origin, destination)
            }
            hideKeyboard()
        }
    }

    private fun setUpAdapters(){
        pickupAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arrayOf("")
        )
        pickupTV.setAdapter(pickupAdapter)


        pickupTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    viewModel.getPickup(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })


        dropOffAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arrayOf("")
        )
        dropOffTV.setAdapter(dropOffAdapter)


        dropOffTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    viewModel.getDropOff(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}