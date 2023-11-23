// MainActivity.kt

package com.example.my_universe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.my_universe.databinding.ActivityMainBinding
import com.example.my_universe.model.CategoryItem
import com.example.my_universe.recycler.CategoryAdapter
import com.example.my_universe.recycler.ImageAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initializeRecyclerView()
        initializeViewPager()
    }

    private fun initializeRecyclerView() {
        // 첫 번째 리사이클러뷰
        val categoryRecyclerView: RecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 예시 텍스트와 이미지 리소스 ID를 포함한 CategoryItem 객체들을 생성
        val categoryItems = listOf(
            CategoryItem(R.drawable.category, "강서구"),
            CategoryItem(R.drawable.category, "사하구"),
            CategoryItem(R.drawable.category, "사상구"),
            CategoryItem(R.drawable.category, "북구"),
            CategoryItem(R.drawable.category, "부산진구"),
            CategoryItem(R.drawable.category, "동래구"),
            CategoryItem(R.drawable.category, "금정구"),
            CategoryItem(R.drawable.category, "해운대구"),
            CategoryItem(R.drawable.category, "기장군"),
        )

        // CategoryAdapter에 CategoryItem 리스트를 전달하여 초기화
        val categoryAdapter = CategoryAdapter(categoryItems)
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun initializeViewPager() {
        // 두 번째 뷰페이저2
        val viewPager: ViewPager2 = binding.imageViewPager
        val imageList = listOf(
            CategoryItem(R.drawable.da, "부산진구"),
            CategoryItem(R.drawable.hae, "부산진구"),
            CategoryItem(R.drawable.i, "부산진구")
        )

        val imageAdapter = ImageAdapter(this, imageList)
        viewPager.adapter = imageAdapter
    }
}
