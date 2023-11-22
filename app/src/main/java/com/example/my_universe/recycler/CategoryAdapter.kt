package com.example.my_universe.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView  // 추가
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.R
import com.example.my_universe.model.CategoryItem  // 추가

class CategoryAdapter(private val items: List<CategoryItem>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryImageView: ImageView = itemView.findViewById(R.id.categoryImageView)
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)  // 추가

        fun bind(item: CategoryItem) {
            categoryImageView.setImageResource(item.imageResId)
            categoryTextView.text = item.text  // 추가
        }
    }
}