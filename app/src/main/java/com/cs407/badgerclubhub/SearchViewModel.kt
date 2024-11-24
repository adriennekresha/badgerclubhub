package com.cs407.badgerclubhub

import android.content.Context
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
    private val _categoriesData = MutableLiveData<Map<String, List<JSONObject>>>()
    //Live data allows fragments to view tha the data
    val categoriesData: LiveData<Map<String, List<JSONObject>>> get() = _categoriesData

    //function to get categories from the api and sort the clubs into categories
    fun sortCategories (context: Context) {
        val winApi = "https://win.wisc.edu/api/discovery/search/organizations"
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, winApi, null, {response ->
            try {
                //all organizations from WIN
                val clubs = response.getJSONArray("value")
                //map to sort clubs by their categories
                val sortedClubsbyCategory = mutableMapOf<String, MutableList<JSONObject>>()
                //iterate through clubs and categorize
                for(i in 0 until clubs.length()){
                    val club = clubs.getJSONObject(i)
                    val categoryNames = club.optJSONArray("CategoryNames")
                    categoryNames?.let {
                        for(j in 0 until it.length()) {
                            val category = it.getString(j)
                            //check if category is already in the map and add if it isn't
                            if(!sortedClubsbyCategory.containsKey(category)){
                                sortedClubsbyCategory[category] = mutableListOf()
                            }
                            sortedClubsbyCategory[category]?.add(club)
                        }
                    }
                }
                _categoriesData.postValue(sortedClubsbyCategory)

            } catch (e: Exception) {e.printStackTrace()}}, {error -> error.printStackTrace()})
        requestQueue.add(jsonObjectRequest)
    }
}