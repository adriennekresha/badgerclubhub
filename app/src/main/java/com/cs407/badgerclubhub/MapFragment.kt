package com.cs407.badgerclubhub;

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapFragment : Fragment() {

    private lateinit var map: GoogleMap
    private lateinit var mDestinationLatLng: LatLng
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_map, container, false)

        // creates map view
        val mapView = view.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        // creates google map from map view
        mapView.getMapAsync { googleMap: GoogleMap ->
            // map is ready
            map = googleMap

            map.setOnMapLoadedCallback{
                map.uiSettings.isMapToolbarEnabled = true
                map.uiSettings.isZoomControlsEnabled = true
                map.uiSettings.isScrollGesturesEnabled = true
            }

            // create marker for Bascom Hall
            mDestinationLatLng = LatLng(43.0753, -89.4034)
            setLocationMarker(mDestinationLatLng, "Bascom Hall")

            // check location permissions and draw a polyline between current location and Bascom Hall
            checkLocationPermission()
        }



        // initialize the fusedLocationProviderClient to access the current location
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)

        return view
    }

    private fun setLocationMarker(destination: LatLng, destinationName: String) {
        // create marker
        map.addMarker(
            MarkerOptions()
                .position(destination)
                .title(destinationName)
        )

        // move camera to marker's location and zoom 15x
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 15f))
    }

    private fun checkLocationPermission() {
        // checks if current location permission is granted
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            // request permission if not granted
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        else {
            mFusedLocationProviderClient.lastLocation
                // check if request is successful
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        // store current location and convert to LatLng
                        val currentLatLong = LatLng(location.latitude, location.longitude)
                        setLocationMarker(currentLatLong, "My Location")
                    }
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_map_home_to_home)
                    true
                }
                R.id.search -> {
                    findNavController().navigate(R.id.action_map_search_to_search)
                    true
                }
                R.id.map -> {
                    true
                }
                R.id.profile -> {
                    findNavController().navigate(R.id.action_map_profile_to_profile)
                    true
                }
                else -> false
            }
        }
    }
}
