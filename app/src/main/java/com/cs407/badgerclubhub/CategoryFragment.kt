package com.cs407.badgerclubhub;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject

@Suppress("DEPRECATION")
class CategoryFragment : Fragment(), ClubAdapter.onClubCardClickListener {
    private lateinit var categoryName: String
    private lateinit var  categoryTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClubAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_category_home_to_home)
                    true
                }
                R.id.search -> {
                    findNavController().navigate(R.id.action_category_search_to_search)
                    true
                }
                R.id.map -> {
                    findNavController().navigate(R.id.action_category_map_to_map)
                    true
                }
                R.id.profile -> {
                    findNavController().navigate(R.id.action_category_profile_to_profile)
                    true
                }

                else -> false
            }
        }
        val clubs = arguments?.getSerializable("clubs_list") as? List<Club> ?: emptyList()
        val categoryName = arguments?.getString("category_name")
        categoryTextView = view.findViewById<TextView>(R.id.categoryTextView)
        categoryTextView.text = categoryName
        adapter = ClubAdapter(clubs, this)
        recyclerView = view.findViewById(R.id.clubsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


    }
    override fun onClubCardClick(club: Club){
        val bundle = Bundle().apply {
            putSerializable("club_info", club)
        }
        findNavController().navigate(R.id.action_category_club_to_club_info, bundle)
    }
}