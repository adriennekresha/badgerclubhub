package com.cs407.badgerclubhub;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import androidx.navigation.fragment.findNavController

class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Calendar
        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        val currentDate = Calendar.getInstance().timeInMillis
        calendarView.date = currentDate

        // Bottom Navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_schedule_home_to_home)
                    true
                }
                R.id.search -> {
                    findNavController().navigate(R.id.action_schedule_search_to_search)
                    true
                }
                R.id.schedule -> {
                    true
                }
                R.id.profile -> {
                    findNavController().navigate(R.id.action_schedule_profile_to_profile)
                    true
                }
                else -> false
            }
        }
    }
}
