package com.example.my_universe.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.databinding.ItemAppointmentBinding

class AppointmentViewHolder (val binding : ItemAppointmentBinding) : RecyclerView.ViewHolder(binding.root)

class AppointmentAdapter (val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AppointmentViewHolder(ItemAppointmentBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int {
        Log.d("kdy","getItemCount : ${datas.size}")
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("kdy","onBindViewHolder : $position")
        val binding = (holder as AppointmentViewHolder).binding
        binding.appointmentText.text = datas[position]
        binding.itemRoot.setOnClickListener {
            Log.d("kdy","item clicked : $position")
        }
    }

}