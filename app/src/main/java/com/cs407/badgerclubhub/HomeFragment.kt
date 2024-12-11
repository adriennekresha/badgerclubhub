package com.cs407.badgerclubhub

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var discoverButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClubAdapter
    private val clubList = mutableListOf<Club>()
    private lateinit var myClubsHeader: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myClubsHeader = view.findViewById(R.id.clubs)

        recyclerView = view.findViewById(R.id.myClubsRecyclerView)

        adapter = ClubAdapter(clubList, object : ClubAdapter.onClubCardClickListener {
            override fun onClubCardClick(club: Club) {
                // Create bundle with club info
                val bundle = Bundle().apply {
                    putSerializable("club_info", club)
                }
                // Navigate to club info page when clicked
                findNavController().navigate(R.id.action_home_club_to_club_info, bundle)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadClubs()

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

    // Load clubs
    override fun onResume() {
        super.onResume()
        loadClubs()
    }

    private fun loadClubs() {
        val sharedPrefs = requireActivity().getSharedPreferences("MyClubs", Context.MODE_PRIVATE)
        val savedClubNames = sharedPrefs.getStringSet("my_clubs", setOf()) ?: setOf()

        // If there are no added clubs
        if (savedClubNames.isEmpty()) return

        // Handle duplicate clubs
        clubList.clear()

        // Add saved clubs to list of my clubs
        savedClubNames.forEach { clubName ->
            val description = sharedPrefs.getString("club_${clubName}_description", "") ?: ""
            val categories = sharedPrefs.getStringSet("club_${clubName}_categories", setOf())?.toList() ?: listOf()

            val club = Club(
                name = clubName,
                description = description,
                categoryNames = categories
            )
            clubList.add(club)
        }

        // Sort My Clubs from A-Z
        clubList.sortBy { it.name.lowercase() }

        // Count of how many clubs are added
        val clubCount = clubList.size
        myClubsHeader.text = "My Clubs ($clubCount)"

        adapter.notifyDataSetChanged()
    }


}