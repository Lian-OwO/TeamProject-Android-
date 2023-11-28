package com.example.my_universe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.my_universe.databinding.FragmentSlidepage05Binding

class AppointmentslidePage05 : Fragment() {
    lateinit var binding: FragmentSlidepage05Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlidepage05Binding.inflate(inflater, container, false)

        return binding.root
    }

}