package com.example.my_universe.MainAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.databinding.ItemImageBinding

//뷰를 모아둔 박스 -> 목록 요소의 뷰,
class MyViewHolder (val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

// 뷰와 데이터 연결 한다.
// 리사이클러뷰에서 , 뷰페이저2에서도 같은 패턴으로 사용할 예정.
// 지금은 더미 데이터 :datas , 공공데이터 내지, 백에서 연결된 데이터
class MyAdapter (val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 리사이클러 뷰의 어댑터를 상속 받으면, 필수적으로 재정의 해야하는 함수들입니다.
    // 자동 완성으로 생성했음.

    // 뷰 생성해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))

    // 출력할 목록 아이템의 크기 (사이즈), 더미 데이터를 사용할 예정.
    override fun getItemCount(): Int {
        Log.d("lsy","getItemCount : ${datas.size}")
        return datas.size
    }

    // 뷰에 데이터를 연동(바인딩) 해주는 작업.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("lsy", "onBindViewHolder : $position")
        val binding = (holder as MyViewHolder).binding
        // 뷰 데이터 출력
        binding.testText.text = datas[position]
        binding.itemRoot.setOnClickListener {
            Log.d("lsy", "item clicked : $position")
        }
    }

}

// 목록 아이템의 요소 뷰를 만들기.