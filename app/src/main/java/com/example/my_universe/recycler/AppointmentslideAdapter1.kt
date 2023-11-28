package com.example.my_universe.recycler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_universe.AppointmentslidePage01
import com.example.my_universe.AppointmentslidePage02
import com.example.my_universe.AppointmentslidePage03

class AppointmentslideAdapter1 (fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AppointmentslidePage01()
            1 -> AppointmentslidePage02()
            else -> AppointmentslidePage03()
        }
    }
}