package com.esaudev.mapsgarden

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.esaudev.mapsgarden.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var map: GoogleMap

    private val locationListAdapter by lazy { LocationListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupListComponents()
        val animator = ObjectAnimator.ofInt(binding.progressBarTest, "progress", 0, 70)
        animator.setDuration(1000)
        animator.interpolator = DecelerateInterpolator()
        animator.start()
        createFragment()
    }

    private fun setupListComponents() {
        with(binding) {
            locationList.apply {
                adapter = locationListAdapter
                LinearSnapHelper().attachToRecyclerView(locationList)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        when (newState) {
                            AbsListView.OnScrollListener.SCROLL_STATE_IDLE -> {
                                val visiblePosition = (locationList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                                val position = (visiblePosition%locationListAdapter.currentList.size)
                                val currentLocation = locationListAdapter.currentList[position]
                                createMarker(location = currentLocation)
                            }

                            else -> Unit
                        }
                    }
                })
            }
        }
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setupLocationList()
    }

    private fun setupLocationList() {
        val locationList = listOf(
            Location(
                title = "Soriana",
                coordinates = LatLng(19.6897173,-99.2266067)
            ),
            Location(
                title = "Walmart",
                coordinates = LatLng(19.6897176,-99.2256028)
            ),
            Location(
                title = "Mercado del Carmen",
                coordinates = LatLng(19.6798232,-99.2197768)
            ),
            Location(
                title = "Luna Parc",
                coordinates = LatLng(19.6619332,-99.214316)
            ),
            Location(
                title = "Suburbia",
                coordinates = LatLng(19.6535748,-99.2107012)
            )
        )

        locationListAdapter.submitList(locationList)
    }

    private fun createMarker(location: Location) {
        val marker = MarkerOptions().position(location.coordinates).title("Test")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(location.coordinates, 18f),
            3000,
            null
        )
    }


}