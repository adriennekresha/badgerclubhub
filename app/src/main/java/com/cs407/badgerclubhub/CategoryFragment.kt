package com.cs407.badgerclubhub;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class CategoryFragment : Fragment() {
    private lateinit var categoryName: String
    private lateinit var clubs: List<JSONObject>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
        //get arguments passed from the search fragment
        arguments?.let {

        }
    }
}
