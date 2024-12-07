package com.cs407.badgerclubhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sign-out
        logoutButton = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_profile_signout_to_login)
        }

        // Bottom Navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_profile_home_to_home)
                    true
                }
                R.id.search -> {
                    findNavController().navigate(R.id.action_profile_search_to_search)
                    true
                }
                R.id.schedule -> {
                    findNavController().navigate(R.id.action_profile_schedule_to_schedule)
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }
}