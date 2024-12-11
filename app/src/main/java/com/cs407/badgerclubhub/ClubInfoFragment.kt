package com.cs407.badgerclubhub

import android.content.Context
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class ClubInfoFragment : Fragment() {

    private lateinit var clubName: String
    private lateinit var clubDescription: String
    private lateinit var addRemoveButton: FloatingActionButton
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_club_info, container, false)
        val club = arguments?.getSerializable("club_info") as? Club
        var nameTextView = view?.findViewById<TextView>(R.id.clubNameTextView)
        var descriptionTextView = view?.findViewById<TextView>(R.id.clubDescriptionTextView)
        // Get club info
        club?.let {
            nameTextView?.text = it.name
            clubName = it.name
            descriptionTextView?.text = it.description
            clubDescription = it.description
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Add clubs
        addRemoveButton = view.findViewById(R.id.addClubButton)
        val club = arguments?.getSerializable("club_info") as? Club

        // Check if the club is already added
        val sharedPrefs = requireActivity().getSharedPreferences("MyClubs", Context.MODE_PRIVATE)
        val savedClubs = sharedPrefs.getStringSet("my_clubs", mutableSetOf()) ?: mutableSetOf()
        val isClubAdded = club?.let { savedClubs.contains(it.name) } ?: false

        // Edit button image depending on if the club is added or not
        if (isClubAdded) {
            addRemoveButton.setImageResource(R.drawable.ic_remove)
        } else {
            addRemoveButton.setImageResource(R.drawable.ic_add)
        }

        addRemoveButton.setOnClickListener {
            club?.let {
                if (isClubAdded) {
                    // Remove club
                    removeClub(it)
                    Toast.makeText(requireContext(), "${it.name} removed from My Clubs", Toast.LENGTH_LONG).show()
                } else {
                    // Add club
                    saveClub(it)
                    Toast.makeText(requireContext(), "${it.name} added to My Clubs", Toast.LENGTH_LONG).show()
                }
                findNavController().navigate(R.id.action_club_info_home_to_home)
            }
        }

        // Bottom navigation
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

    }

    // Function to save a club to My Clubs
    private fun saveClub(club: Club) {
        val sharedPrefs = requireActivity().getSharedPreferences("MyClubs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val clubs = sharedPrefs.getStringSet("my_clubs", mutableSetOf()) ?: mutableSetOf()
        clubs.add(club.name)
        editor.putStringSet("my_clubs", clubs)
        editor.putString("club_${club.name}_description", club.description)
        editor.putStringSet("club_${club.name}_categories", club.categoryNames.toSet())
        editor.apply()

        // Add club to Firebase under the logged in user
        auth.currentUser?.let { user ->
            val clubData = mapOf(
                "name" to club.name,
                "description" to club.description,
                "categoryNames" to club.categoryNames
            )

            database.child("users")
                .child(user.uid)
                .child("savedClubs")
                .child(club.name)
                .setValue(clubData)
        }
    }

    // Function to remove a club from My Clubs
    private fun removeClub(club: Club) {
        val sharedPrefs = requireActivity().getSharedPreferences("MyClubs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val clubs = sharedPrefs.getStringSet("my_clubs", mutableSetOf()) ?: mutableSetOf()
        clubs.remove(club.name)
        editor.putStringSet("my_clubs", clubs)
        editor.remove("club_${club.name}_description")
        editor.remove("club_${club.name}_categories")
        editor.apply()

        // Remove club from Firebase
        auth.currentUser?.let { user ->
            database.child("users")
                .child(user.uid)
                .child("savedClubs")
                .child(club.name)
                .removeValue()
        }
    }


}