package com.cs407.badgerclubhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("DEPRECATION")
class ClubInfoFragment : Fragment() {

    private lateinit var clubName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_club_info, container, false)
        val club = arguments?.getSerializable("club_info") as? Club
        var nameTextView = view?.findViewById<TextView>(R.id.clubNameTextView)
        var descriptionTextView = view?.findViewById<TextView>(R.id.clubDescriptionTextView)
        club?.let {
            nameTextView?.text = it.name
            clubName = it.name
            descriptionTextView?.text = it.description
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_club_info_home_to_home)
                    true
                }
                R.id.search -> {
                    findNavController().navigate(R.id.action_club_info_search_to_search)
                    true
                }
                R.id.map -> {
                    findNavController().navigate(R.id.action_club_info_map_to_map)
                    true
                }
                else -> false
            }
        }

        // Add Club
        val addClubButton = view.findViewById<FloatingActionButton>(R.id.addClubButton)
        addClubButton.setOnClickListener {
            Toast.makeText(requireContext(), "$clubName added to My Hub", Toast.LENGTH_SHORT).show()
        }
    }
}