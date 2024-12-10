package com.cs407.badgerclubhub;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.findNavController

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
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
