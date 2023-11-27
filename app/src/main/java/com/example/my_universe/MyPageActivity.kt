// MyPageActivity.kt
package com.example.my_universe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_universe.databinding.ActivityMyPageBinding
import com.example.my_universe.model.MyPageItem
import com.example.my_universe.recycler.MyPageAdapter

class MyPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 예약 데이터 생성 (임의의 데이터)
        val myPageItems = listOf(
            MyPageItem("더미아이디", "abcdefghijk@example.com", "더미더미"),
            // 필요한 만큼의 데이터를 추가하세요.
        )

        // 어댑터 생성 및 설정
        val adapter = MyPageAdapter(myPageItems)

        // recyclerView를 binding 객체를 통해 참조
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
