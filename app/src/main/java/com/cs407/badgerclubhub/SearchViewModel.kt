package com.cs407.badgerclubhub

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class SearchViewModel :ViewModel() {
    //MutableLiveData object for category data from API
    private val _categoriesMap = MutableLiveData<Map<String, List<Club>>>()
    //Live data allows fragments to view tha the data
    val categoriesMap: LiveData<Map<String, List<Club>>> get() = _categoriesMap
    private val _allClubs = MutableLiveData<List<Club>>()
    val allClubs:  LiveData<List<Club>> = _allClubs
    private val winAPI = "https://win.wisc.edu/api/discovery/search/organizations?query&top=3000&filter"
    //function to get categories from the api and sort the clubs into categories
    fun sortCategories (context: Context) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(Request.Method.GET, winAPI, null, {response ->
            try {

                //all organizations from WIN
                val clubs = response.getJSONArray("value")
                //map to sort clubs by their categories
                val sortedClubsByCategory = mutableMapOf<String, MutableList<Club>>()
                //iterate through clubs and categorize
                for(i in 0 until clubs.length()) {
                    val clubJSON = clubs.getJSONObject(i)
                    val club = Club(
                        name = clubJSON.getString("Name"),
                        description = clubJSON.getString("Description"),
                        categoryNames = clubJSON.getJSONArray("CategoryNames").let { jsonArray ->
                            List(jsonArray.length()) { index -> jsonArray.getString(index) }
                        }
                    )

                    club.categoryNames.forEach { category ->
                        sortedClubsByCategory.getOrPut(category) {mutableListOf()}
                            .add(club)                    }


                }
                _categoriesMap.postValue(sortedClubsByCategory.toMap())

            } catch (e: Exception) {e.printStackTrace()}}, {error -> error.printStackTrace()})
        requestQueue.add(request)
    }
    // method to get all clubs from API
    fun getAllClubs(context: Context) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(Request.Method.GET, winAPI, null, { response ->
            //get clubs from the API
            val clubsArray = response.getJSONArray("value")
            val clubs = mutableListOf<Club>()
            //loop through list to get desired data
            for (i in 0 until clubsArray.length()){
                val clubJSON = clubsArray.getJSONObject(i)
                val club = Club(
                    name = clubJSON.getString("Name"),
                    description = clubJSON.getString("Description"),
                    categoryNames = clubJSON.getJSONArray("CategoryNames").let { jsonArray ->
                        List(jsonArray.length()) { index -> jsonArray.getString(index) }
                    }
                )
                clubs.add(club)
            }
        }, {error -> error.printStackTrace()})
        requestQueue.add(request)
    }
}