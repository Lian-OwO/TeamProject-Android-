package com.example.my_universe.recycler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_universe.AppointmentslidePage04
import com.example.my_universe.AppointmentslidePage05
import com.example.my_universe.AppointmentslidePage06

class AppointmentslideAdapter2 (fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AppointmentslidePage04()
            1 -> AppointmentslidePage05()
            else -> AppointmentslidePage06()
        }
    }
}