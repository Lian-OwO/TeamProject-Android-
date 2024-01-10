package com.my_universe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.my_universe.mainFragment.MapFragment
import com.my_universe.databinding.ActivityReserveResultBinding
import com.my_universe.recycler.ReserveslideAdapter2

class ReserveResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityReserveResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveResultBinding.inflate(layoutInflater)
        setContentView(binding.root)



////////// '숙소문의' 버튼
        binding.buttonReserveReturn.setOnClickListener {
            val intent = Intent(this@ReserveResultActivity, ReserveActivity::class.java)
            startActivity(intent)
        }

////////// '지도보기' 버튼
        binding.buttonReserveMap.setOnClickListener {
            val mapFragment = MapFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            // FrameLayout 등의 컨테이너에 MapFragment를 추가
            transaction.replace(R.id.fragmentContainer, mapFragment)

            // FragmentTransaction 완료
            transaction.commit()
        }



////////// 이미지 슬라이드
        binding.viewPager02.adapter = ReserveslideAdapter2(this)
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