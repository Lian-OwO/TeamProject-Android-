package com.my_universe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my_universe.databinding.FragmentReserveslidePage04Binding

class ReserveslidePage04 : Fragment() {
    lateinit var binding: FragmentReserveslidePage04Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReserveslidePage04Binding.inflate(inflater, container, false)

        return binding.root
    }


}