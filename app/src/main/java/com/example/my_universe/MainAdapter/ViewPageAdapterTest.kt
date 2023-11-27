package com.example.my_universe.MainAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_universe.MainFragment.Test1Fragment
import com.example.my_universe.MainFragment.Test2Fragment
import com.example.my_universe.MainFragment.Test3Fragment

class ViewPageAdapterTest(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {

    //각 프래그먼트들을 담을 리스트
    var testFragment : List<Fragment>

    init {
        testFragment = listOf(Test1Fragment(), Test2Fragment(), Test3Fragment())
    }

    override fun getItemCount(): Int {
        return testFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        val returnFragment : Fragment = testFragment[position]
        return returnFragment
    }

}