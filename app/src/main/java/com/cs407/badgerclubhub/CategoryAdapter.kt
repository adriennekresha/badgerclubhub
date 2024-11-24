package com.cs407.badgerclubhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.json.JSONObject

class CategoryAdapter (private val clubs: List<JSONObject>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.club_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val club = clubs[position]
        holder.clubNameTextView.text = club.getString("Name")
        holder.clubDescription.text = club.optString("Description", "No Description Available")

    }
    override fun getItemCount():Int {
        return clubs.size
    }
    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val clubNameTextView: TextView = itemView.findViewById(R.id.clubNameTextView)
        val clubDescription: TextView = itemView.findViewById(R.id.descriptionTextView)
    }
}