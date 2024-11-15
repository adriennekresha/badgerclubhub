package com.cs407.badgerclubhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    private lateinit var discoverButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        discoverButton = view.findViewById(R.id.discoverButton)
        discoverButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_discover_to_search)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.bottom_menu, menu);
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.search -> {
                findNavController().navigate(R.id.action_home_search_to_search)
                true
            }

            R.id.schedule -> {
                findNavController().navigate(R.id.action_home_schedule_to_schedule)
                true
            }

            else -> false
        }
    }

}