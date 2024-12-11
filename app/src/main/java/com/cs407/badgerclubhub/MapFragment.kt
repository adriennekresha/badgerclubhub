package com.cs407.badgerclubhub;

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : Fragment() {

    private lateinit var map: GoogleMap
    private lateinit var mBascomLatLng: LatLng
    private lateinit var mMemULatLng : LatLng
    private lateinit var mRedGymnLatLng : LatLng
    private lateinit var mNickLatLng: LatLng
    private lateinit var mCSLatLng: LatLng
    private lateinit var mColLibLatLng: LatLng
    private lateinit var mEHallLatLng: LatLng
    private lateinit var mBakkeLatLng: LatLng

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

            map.uiSettings.isMapToolbarEnabled = true
            map.uiSettings.isZoomControlsEnabled = true
            map.uiSettings.isScrollGesturesEnabled = true
            // create marker for Bascom Hall
            mBascomLatLng = LatLng(43.0753, -89.4034)
            setLocationMarker(mBascomLatLng, "Bascom Hall")
            //create marker for Memorial Union

            mMemULatLng = LatLng(43.076666740089266, -89.40007667727993)
            setLocationMarker(mMemULatLng, "Memorial Union")
            //create marker for red gym
            mRedGymnLatLng = LatLng(43.0762, -89.3984)
            setLocationMarker(mRedGymnLatLng, "Red Gym")
            //create location marker for nicholas recreation center
            mNickLatLng = LatLng(43.0709, -89.3991)
            setLocationMarker(mNickLatLng, "Nicholas Recreation Center")
            //create location marker for computer science building
            mCSLatLng = LatLng(43.0717, -89.4067)
            setLocationMarker(mCSLatLng, "Computer Science Building")
            //create location marker for College Library
            mColLibLatLng = LatLng(43.0770, -89.4013)
            setLocationMarker(mColLibLatLng, "College Library")
            //create location marker for Engineering Hall
            mEHallLatLng = LatLng(43.0719, -89.4103)
            setLocationMarker(mEHallLatLng, "Engineering Hall")
            //set location for the Bakke
            mBakkeLatLng = LatLng(43.0769, -89.4202)
            setLocationMarker(mBakkeLatLng, "Bakke Recreate and Wellbeing Center")


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
