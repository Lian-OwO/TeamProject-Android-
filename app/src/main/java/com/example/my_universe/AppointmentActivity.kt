package com.example.my_universe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_universe.databinding.ActivityAppointmentBinding

class AppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}