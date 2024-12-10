package com.cs407.badgerclubhub

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var discoverButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Discover Clubs
        discoverButton = view.findViewById(R.id.discoverButton)
        discoverButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_discover_to_search)
        }

        // Bottom Navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.search -> {
                    findNavController().navigate(R.id.action_home_search_to_search)
                    true
                }
                R.id.map -> {
                    findNavController().navigate(R.id.action_home_map_to_map)
                    true
                }
                R.id.profile -> {
                    findNavController().navigate(R.id.action_home_profile_to_profile)
                    true
                }
                else -> false
            }
        }
    }
}