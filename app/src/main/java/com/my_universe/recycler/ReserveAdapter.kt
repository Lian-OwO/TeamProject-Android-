package com.my_universe.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my_universe.databinding.ItemReserveBinding

class ReserveViewHolder (val binding : ItemReserveBinding) : RecyclerView.ViewHolder(binding.root)

class ReserveAdapter (val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReserveViewHolder(ItemReserveBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int {
        Log.d("kdy","getItemCount : ${datas.size}")
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("kdy","onBindViewHolder : $position")
        val binding = (holder as ReserveViewHolder).binding
        binding.reserveText.text = datas[position]
        binding.itemRoot.setOnClickListener {
            Log.d("kdy","item clicked : $position")
        }
    }

}