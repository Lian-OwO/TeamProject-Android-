package com.my_universe.recycler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.my_universe.ReserveslidePage04
import com.my_universe.ReserveslidePage05
import com.my_universe.ReserveslidePage06

class ReserveslideAdapter2 (fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ReserveslidePage04()
            1 -> ReserveslidePage05()
            else -> ReserveslidePage06()
        }
    }
}