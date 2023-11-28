package com.example.my_universe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.my_universe.databinding.FragmentSlidepage04Binding

class AppointmentslidePage04 : Fragment() {
    lateinit var binding: FragmentSlidepage04Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlidepage04Binding.inflate(inflater, container, false)

        return binding.root
    }

}