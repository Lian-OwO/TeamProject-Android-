package com.example.my_universe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.my_universe.databinding.ActivityAppointmentResultBinding

class AppointmentResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppointmentResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}