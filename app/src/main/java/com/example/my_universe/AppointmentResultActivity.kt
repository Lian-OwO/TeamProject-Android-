package com.example.my_universe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_universe.databinding.ActivityAppointmentBinding
import com.example.my_universe.databinding.ActivityAppointmentResultBinding

class AppointmentResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppointmentResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}