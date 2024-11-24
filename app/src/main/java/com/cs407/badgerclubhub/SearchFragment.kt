package com.cs407.badgerclubhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.findNavController

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_search_home_to_home)
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.schedule -> {
                    findNavController().navigate(R.id.action_search_schedule_to_schedule)
                    true
                }
                else -> false
            }
        }

        bottomNav.selectedItemId = R.id.search

        return view
    }
}