package com.example.my_universe.MainAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.ApiModel.BoardModel
import com.example.my_universe.ApiModel.ItemModel2
import com.example.my_universe.ApiModel.ItemModel77

import com.example.my_universe.databinding.ItemImageBinding

//뷰를 모아둔 박스 -> 목록 요소의 뷰,
class MyViewHolder3 (val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

// 뷰와 데이터 연결 한다.
// 리사이클러뷰에서 , 뷰페이저2에서도 같은 패턴으로 사용할 예정.
// 지금은 더미 데이터 :datas , 공공데이터 내지, 백에서 연결된 데이터
class MyAdapter3 (val context: Context, val datas: List<ItemModel77>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 리사이클러 뷰의 어댑터를 상속 받으면, 필수적으로 재정의 해야하는 함수들입니다.
    // 자동 완성으로 생성했음.

    // 뷰 생성해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder3(ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))

    // 출력할 목록 아이템의 크기 (사이즈), 더미 데이터를 사용할 예정.
    override fun getItemCount(): Int {
        Log.d("lsy","getItemCount : ${datas.size}")
        return datas.size
    }

    // 뷰에 데이터를 연동(바인딩) 해주는 작업.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("lsy", "onBindViewHolder : $position")
        val binding = (holder as MyViewHolder3).binding
        // 뷰 데이터 출력
        binding.testText.text = datas[position].MAIN_TITLE
        binding.descriptionText.text = datas[position].TITLE

        //viewPager2 넣기
//        binding.tapFragSliderviewPager1.adapter =
//            datas[position].MAIN_IMG_NORMAL?.let { MyAdapter2(context, it) }
        var imgList : MutableList<String> = mutableListOf<String>()
        datas[position].MAIN_IMG_NORMAL?.let { imgList.add(it) }
        datas[position].MAIN_IMG_THUMB?.let { imgList.add(it) }
        binding.tapFragSliderviewPager1.adapter = MyAdapter2(context,imgList)


//        binding.itemRoot.setOnClickListener {
//            Log.d("lsy", "item clicked : $position")
//        }
    }

}

// 목록 아이템의 요소 뷰를 만들기.