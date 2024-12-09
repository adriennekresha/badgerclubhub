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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClubInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class ClubInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

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
                R.id.schedule -> {
                    findNavController().navigate(R.id.action_club_info_schedule_to_schedule)
                    true
                }
                else -> false
            }
        }

        // Add Club
        val addClubButton = view.findViewById<FloatingActionButton>(R.id.addClubButton)
        addClubButton.setOnClickListener {
            Toast.makeText(requireContext(), "Club added to My Hub", Toast.LENGTH_SHORT).show()
        }
    }
}