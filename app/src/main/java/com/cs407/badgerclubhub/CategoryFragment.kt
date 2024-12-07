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

class CategoryFragment : Fragment() {
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
                R.id.schedule -> {
                    findNavController().navigate(R.id.action_category_search_to_search)
                    true
                }
                else -> false
            }
        }
        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        categoryName = arguments?.getString("category_name").toString()
        categoryTextView = view.findViewById(R.id.categoryTextView)
        categoryTextView.text = categoryName
        val clubs = arguments?.getSerializable("clubs_list") as? ArrayList<Club>
        recyclerView = view.findViewById(R.id.clubsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        clubs?.let { adapter = ClubAdapter(it) }
//
    }
}