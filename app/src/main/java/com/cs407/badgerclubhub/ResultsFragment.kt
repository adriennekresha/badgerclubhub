package com.cs407.badgerclubhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.ArrayList

@Suppress("DEPRECATION")
class ResultsFragment : Fragment(), ClubAdapter.onClubCardClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClubAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
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

                R.id.schedule -> {
                    findNavController().navigate(R.id.action_category_search_to_search)
                    true
                }

                else -> false
            }
        }

        val results = arguments?.getSerializable("search_results") as List<Club>
        recyclerView = view.findViewById(R.id.resultsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ClubAdapter(results, this)
        recyclerView.adapter = adapter
    }

    override fun onClubCardClick(club: Club) {
        val bundle = Bundle().apply {
            putSerializable("club_info", club)
        }
        findNavController().navigate(R.id.action_results_club_to_club_info, bundle)
    }

}