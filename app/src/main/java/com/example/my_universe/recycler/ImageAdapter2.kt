// ImageAdapter2.kt
package com.example.my_universe.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_universe.R
import com.example.my_universe.model.CategoryItem

class ImageAdapter2(private val context: Context, private val imageList: List<CategoryItem>) :
    RecyclerView.Adapter<ImageAdapter2.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // CategoryItem에서 이미지 리소스 ID 가져오기
        val imageResId = imageList[position].imageResId
        // 이미지 로드
        Glide.with(context)
            .load(imageResId)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
