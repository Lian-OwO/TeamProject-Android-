package com.example.my_universe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_universe.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
