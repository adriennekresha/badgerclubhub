package com.cs407.badgerclubhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(){
    private lateinit var discoverButton:Button
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
}