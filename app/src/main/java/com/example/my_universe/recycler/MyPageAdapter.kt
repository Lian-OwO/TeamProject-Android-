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
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.EmailView)
//        val nameTextView: TextView = itemView.findViewById(R.id.NameView)
//        val phoneNumberView: TextView = itemView.findViewById(R.id.PhoneNumberView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_page, parent, false)
        return MyPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        val item = items[position]

        // 데이터를 뷰에 바인딩
        holder.idTextView.text = holder.itemView.context.getString(R.string.id_label, item.uid)
        holder.emailTextView.text = holder.itemView.context.getString(R.string.email_label, item.email)
//        holder.nameTextView.text = holder.itemView.context.getString(R.string.name_label, item.name)
//        holder.phoneNumberView.text = holder.itemView.context.getString(R.string.phone_number_label, item.phoneNumber)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
