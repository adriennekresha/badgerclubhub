package com.cs407.badgerclubhub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.Serializable

class SearchFragment : Fragment() {
    //searchViewModel variable
    private lateinit var viewModel: SearchViewModel
    //category buttons
    private lateinit var academic_career: ImageButton
    private lateinit var activism_advocacy: ImageButton
    private lateinit var agriculture_environmental: ImageButton
    private lateinit var arts_music: ImageButton
    private lateinit var cultural_ethnic: ImageButton
    private lateinit var graduate_professional: ImageButton
    private lateinit var health_wellness: ImageButton
    private lateinit var media_publication: ImageButton
    private lateinit var political_interest: ImageButton
    private lateinit var religious_spiritual: ImageButton
    private lateinit var service_volunteer: ImageButton
    private lateinit var social_fraternity_sorority: ImageButton
    private lateinit var sports_recreation: ImageButton

    private lateinit var clubsRecyclerView: RecyclerView
    private lateinit var adapter: ClubAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_search_home_to_home)
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.schedule -> {
                    findNavController().navigate(R.id.action_search_schedule_to_schedule)
                    true
                }
                else -> false
            }
        }

        //initialize view model and recycler view
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        clubsRecyclerView = view.findViewById(R.id.clubsRecyclerView)
        clubsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.allClubs.observe(viewLifecycleOwner, Observer { clubs ->
            adapter = ClubAdapter(clubs, this)
            clubsRecyclerView.adapter = adapter
        })


        //initialize buttons
        academic_career = view.findViewById(R.id.academic_career)
        activism_advocacy = view.findViewById(R.id.activism_advocacy)
        agriculture_environmental = view.findViewById(R.id.agriculture_environmental)
        arts_music = view.findViewById(R.id.arts_music)
        cultural_ethnic = view.findViewById(R.id.cultural_ethnic)
        graduate_professional = view.findViewById(R.id.graduate_professional)
        health_wellness = view.findViewById(R.id.health_wellness)
        media_publication = view.findViewById(R.id.media_publication)
        political_interest = view.findViewById(R.id.political_interest)
        religious_spiritual = view.findViewById(R.id.religious_spiritual)
        service_volunteer = view.findViewById(R.id.service_volunteer)
        social_fraternity_sorority = view.findViewById(R.id.social_fraternity_sorority)
        sports_recreation = view.findViewById(R.id.sports_recreation)

        viewModel.sortCategories(this.requireContext())
        viewModel.categoriesMap.observe(viewLifecycleOwner) {
                categoriesMap -> populateButtons(categoriesMap as Map<String, MutableList<Club>>)
        }

        viewModel.getAllClubs(this.requireContext())
    }

    private fun populateButtons (categoriesMap: Map<String, MutableList<Club>>) {
        academic_career.setOnClickListener {
           navToCategory("Academic/Career", categoriesMap["Academic/Career"]?: emptyList())
        }
        activism_advocacy.setOnClickListener {
            navToCategory("Activism/Advocacy", categoriesMap["Activism/Advocacy"]?: emptyList())
        }
        agriculture_environmental.setOnClickListener {
            navToCategory("Agricultural/Environmental", categoriesMap["Agricultural/Environmental"]?: emptyList())
        }
        arts_music.setOnClickListener {
           navToCategory("Arts/Music", categoriesMap["Arts/Music"]?: emptyList())
        }
        cultural_ethnic.setOnClickListener {
            navToCategory("Cultural/Ethnic", categoriesMap["Cultural/Ethnic"]?: emptyList())
        }
        graduate_professional.setOnClickListener {
            navToCategory("Graduate/Professional", categoriesMap["Graduate/Professional"]?: emptyList())
        }
        health_wellness.setOnClickListener {
            navToCategory("Health/Wellness", categoriesMap["Health/Welness"]?: emptyList())
        }
        media_publication.setOnClickListener {
            navToCategory("Media/Publication", categoriesMap["Media/Publication"]?: emptyList())
        }
        political_interest.setOnClickListener {
            navToCategory("Political Interest", categoriesMap["Political Interest"]?: emptyList())
        }
        religious_spiritual.setOnClickListener {
            navToCategory("Religious/Spiritual", categoriesMap["Religious/Spiritual"]?: emptyList())
        }
        service_volunteer.setOnClickListener {
            navToCategory("Service/Volunteer", categoriesMap["Service/Volunteer"]?: emptyList())
        }
        social_fraternity_sorority.setOnClickListener {
            navToCategory("Social Fraternity/Sorority", categoriesMap["Social Fraternity/Sorority"]?: emptyList())
        }
        sports_recreation.setOnClickListener {
            navToCategory("Sports/Recreation", categoriesMap["Sports/Recreation"]?: emptyList())
        }
    }

    //navigate from buttons to category fragment
    private fun navToCategory(category: String, clubs: List<Club>){

        val bundle = Bundle().apply {
            putString("category_name", category)
            putSerializable("clubs_list", ArrayList(clubs))
        }

        findNavController().navigate(R.id.action_search_category_to_category, bundle)
    }
}