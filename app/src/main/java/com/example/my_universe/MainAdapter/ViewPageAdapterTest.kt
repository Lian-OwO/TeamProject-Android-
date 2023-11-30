package com.example.my_universe.MainAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_universe.MainFragment.HomeFragment


class ViewPageAdapterTest(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {

    //각 프래그먼트들을 담을 리스트
    var test1Fragment : List<Fragment>

    init {
        test1Fragment = listOf(HomeFragment())
    }

    override fun getItemCount(): Int {
        return test1Fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        val returnFragment : Fragment = test1Fragment[position]
        return returnFragment
    }

}