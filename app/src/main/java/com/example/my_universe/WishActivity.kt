package com.example.my_universe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_universe.databinding.ActivityWishBinding

class WishActivity : AppCompatActivity() {
    lateinit var binding: ActivityWishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}


