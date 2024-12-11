package com.cs407.badgerclubhub

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
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
import java.util.Timer
import java.util.TimerTask

class SearchFragment : Fragment(), ClubAdapter.onClubCardClickListener {
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
    private lateinit var searchBar: EditText

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
                R.id.map -> {
                    findNavController().navigate(R.id.action_search_map_to_map)
                    true
                }
                R.id.profile -> {
                    findNavController().navigate(R.id.action_search_profile_to_profile)
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
        //implement search functionality
        viewModel.getAllClubs(this.requireContext())
        searchBar = view.findViewById<EditText>(R.id.search_bar)
        searchBar.requestFocus()

        searchBar.setOnKeyListener { view, keyCode, keyEvent ->

            if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action== KeyEvent.ACTION_UP){
                Log.d("SEARCH", "Enter key detected")
                val searchTerm = searchBar.getText().toString()
                Log.d("SEARCH", "Search term entered: $searchTerm")
                viewModel.allClubs.observe(viewLifecycleOwner) {allClubs ->
                    if(allClubs.isNullOrEmpty()){
                        Log.e("SEARCH", "No clubs available in ViewModel")
                    }
                    else {
                        Log.d("SEARCH", "Clubs fetched: ${allClubs.size}")
                        search(searchBar, searchTerm, allClubs as ArrayList<Club>)
                    }
                }
                true
            }
                false
        }
    }

    private fun populateButtons (categoriesMap: Map<String, MutableList<Club>>) {
        academic_career.setOnClickListener {
           navToCategory("Academic/Career", categoriesMap["Academic/Career"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        activism_advocacy.setOnClickListener {
            navToCategory("Activism/Advocacy", categoriesMap["Activism/Advocacy"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        agriculture_environmental.setOnClickListener {
            navToCategory("Agricultural/Environmental", categoriesMap["Agricultural/Environmental"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        arts_music.setOnClickListener {
           navToCategory("Arts/Music", categoriesMap["Arts/Music"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        cultural_ethnic.setOnClickListener {
            navToCategory("Cultural/Ethnic", categoriesMap["Cultural/Ethnic"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        graduate_professional.setOnClickListener {
            navToCategory("Graduate/Professional", categoriesMap["Graduate/Professional"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        health_wellness.setOnClickListener {
            navToCategory("Health/Wellness", categoriesMap["Health/Wellness"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        media_publication.setOnClickListener {
            navToCategory("Media/Publication", categoriesMap["Media/Publication"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        political_interest.setOnClickListener {
            navToCategory("Political Interest", categoriesMap["Political Interest"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        religious_spiritual.setOnClickListener {
            navToCategory("Religious/Spiritual", categoriesMap["Religious/Spiritual"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        service_volunteer.setOnClickListener {
            navToCategory("Service/Volunteer", categoriesMap["Service/Volunteer"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        social_fraternity_sorority.setOnClickListener {
            navToCategory("Social Fraternity/Sorority", categoriesMap["Social Fraternity/Sorority"]?.sortedBy { it.name.lowercase() } ?: emptyList())
        }
        sports_recreation.setOnClickListener {
            navToCategory("Sports/Recreation", categoriesMap["Sports/Recreation"]?.sortedBy { it.name.lowercase() } ?: emptyList())
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

    //search bar functionality
    private fun search(searchBar: EditText, searchTerm: String, allClubs: ArrayList<Club>) {
        var searchResults = ArrayList<Club>()
        for (club in allClubs) {
            if(club.name.toString().contains(searchTerm, ignoreCase = true) || club.description.toString().contains(searchTerm, ignoreCase = true)
                || club.categoryNames.contains(searchTerm)) {
                searchResults.add(club)

            }
        }
        if(!searchResults.isEmpty()){
            val bundle = Bundle().apply {
                putSerializable("search_results", searchResults)
            }
            findNavController().navigate(R.id.action_search_to_results, bundle)
        }
        else {
            Toast.makeText(activity, "No Results Found", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onClubCardClick(club: Club){
        val bundle = Bundle().apply {
            putSerializable("club_info", club)
        }
        findNavController().navigate(R.id.action_search_club_to_club_info, bundle)
    }
}