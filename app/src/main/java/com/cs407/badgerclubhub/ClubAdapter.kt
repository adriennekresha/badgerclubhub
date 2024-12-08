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


class ClubAdapter (private val clubs: List<Club>, private val listener: onClubCardClickListener) :
    RecyclerView.Adapter<ClubAdapter.ClubViewHolder>() {
        interface onClubCardClickListener {
            fun onClubCardClick(club:Club)
        }
        inner class ClubViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val name = itemView.findViewById<TextView>(R.id.clubNameTextView)
            val description = itemView.findViewById<TextView>(R.id.descriptionTextView)
            fun bind (club: Club){
                name.text = club.name
                var clubDescriptionShort = if (club.description.length > 200) {
                    club.description.substring(0,200) + "..."
                }
                else{
                    club.description
                }

                if (clubDescriptionShort.equals(null)) {
                    clubDescriptionShort = "No Description Available"
                }
                description?.text = clubDescriptionShort
                itemView.setOnClickListener{
                    listener.onClubCardClick(club)
                }
           }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.club_item, parent, false)
        return ClubViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
       holder.bind(clubs[position])

    }
    override fun getItemCount():Int {
        return clubs.size
    }


}