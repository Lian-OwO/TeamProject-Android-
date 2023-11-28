package com.example.my_universe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.my_universe.databinding.ActivityAppointmentBinding
import com.example.my_universe.recycler.AppointmentAdapter
import com.example.my_universe.recycler.AppointmentslideAdapter1

class AppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<String>()

        for (i in 1..3) {
            datas.add("침실   \n퀸사이즈침대 $i")
        }

//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = AppointmentAdapter(datas)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))





        // 이미지 슬라이드
        binding.viewPager01.adapter = AppointmentslideAdapter1(this)
        // 처음, 마지막 페이지간 양방향 이동 가능
        binding.viewPager01.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            var currentState = 0
            var currentPos = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position) {
                    if(currentPos == 0) binding.viewPager01.currentItem = 2
                    else if(currentPos == 2) binding.viewPager01.currentItem = 0
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                currentPos = position
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                currentState = state
                super.onPageScrollStateChanged(state)
            }
        })

    }

}
