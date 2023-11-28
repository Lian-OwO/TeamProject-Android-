package com.example.my_universe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.my_universe.databinding.FragmentSlidepage03Binding

class AppointmentslidePage03 : Fragment() {
    lateinit var binding: FragmentSlidepage03Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlidepage03Binding.inflate(inflater, container, false)

        return binding.root
    }

}