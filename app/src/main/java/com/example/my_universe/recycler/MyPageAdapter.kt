package com.example.my_universe.recycler

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.MyPageActivity
import com.example.my_universe.R
import com.example.my_universe.databinding.ActivityMyPageBinding
import com.example.my_universe.model.MyPageItem

class MyPageViewHolder (private val binding: ActivityMyPageBinding) : RecyclerView.ViewHolder(binding.root)
class MyPageAdapter(val datas: ArrayList<Uri>, val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyPageViewHolder(
            ActivityMyPageBinding.inflate(
                LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}