// MyPageAdapter.kt
package com.example.my_universe.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.R
import com.example.my_universe.model.MyPageItem

class MyPageAdapter(private val items: List<MyPageItem>) :
    RecyclerView.Adapter<MyPageAdapter.MyPageViewHolder>() {

    inner class MyPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.IdView)
        val emailTextView: TextView = itemView.findViewById(R.id.EmailView)
        val nameTextView: TextView = itemView.findViewById(R.id.NameView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_page, parent, false)
        return MyPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        val item = items[position]

        // 데이터를 뷰에 바인딩
        holder.idTextView.text = "아이디: ${item.id}"
        holder.emailTextView.text = "이메일: ${item.email}"
        holder.nameTextView.text = "이름: ${item.name}"

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
