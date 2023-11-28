package com.example.my_universe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.my_universe.databinding.ActivityAppointmentResultBinding
import com.example.my_universe.recycler.AppointmentslideAdapter2

class AppointmentResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppointmentResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)



// 이미지 슬라이드
        binding.viewPager02.adapter = AppointmentslideAdapter2(this)
        // 처음, 마지막 페이지간 양방향 이동 가능
        binding.viewPager02.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            var currentState = 0
            var currentPos = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position) {
                    if(currentPos == 0) binding.viewPager02.currentItem = 2
                    else if(currentPos == 2) binding.viewPager02.currentItem = 0
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