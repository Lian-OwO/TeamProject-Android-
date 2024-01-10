package com.my_universe.mainAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my_universe.apiModel.ItemModel77
import com.my_universe.databinding.ItemImageBinding

class MyViewHolder3(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter3(val context: Context, val datas: MutableList<ItemModel77>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {




    fun setData(newItems: List<ItemModel77>) {
        datas.clear()
        datas.addAll(newItems)
        notifyDataSetChanged()
    }
    fun addData(newItems: List<ItemModel77>) {
        datas.addAll(newItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder3(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int {
        Log.d("lsy", "getItemCount : ${datas.size}")
        return datas.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("lsy", "onBindViewHolder : $position")
        val binding = (holder as MyViewHolder3).binding

        binding.testText.text = datas[position].MAIN_TITLE
        binding.descriptionText.text = datas[position].TITLE
//        binding.descriptionText.text = datas[position].GUGUN_NM





        var imgList: MutableList<String> = mutableListOf<String>()
        datas[position].MAIN_IMG_NORMAL?.let { imgList.add(it) }
        datas[position].MAIN_IMG_THUMB?.let { imgList.add(it) }
        binding.tapFragSliderviewPager1.adapter = MyAdapter2(context, imgList)
    }
}
