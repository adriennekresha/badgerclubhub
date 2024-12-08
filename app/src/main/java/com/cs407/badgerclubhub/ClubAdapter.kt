package com.cs407.badgerclubhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject


class ClubAdapter (private val clubs: List<Club>, private val fragment: Fragment) :
    RecyclerView.Adapter<ClubAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.club_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val club = clubs[position]
        var clubDescriptionShort = if (club.description.length > 200) {
            club.description.substring(0,200) + "..."
        }
        else{
            club.description
        }

        if (clubDescriptionShort.equals(null)) {
            clubDescriptionShort = "No Description Available"
        }
        holder.clubNameTextView.text = club.name
        holder.clubDescription.text = clubDescriptionShort
        holder.itemView.setOnClickListener {
            view -> val bundle = Bundle().apply {
                putSerializable("club_info", club)
        }
            fragment.findNavController().navigate(R.id.action_category_club_to_club_info, bundle) }
        //add categories here too

    }
    override fun getItemCount():Int {
        return clubs.size
    }
    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val clubNameTextView: TextView = itemView.findViewById(R.id.clubNameTextView)
        val clubDescription: TextView = itemView.findViewById(R.id.descriptionTextView)
        //need to add categories to the club cards
    }

}