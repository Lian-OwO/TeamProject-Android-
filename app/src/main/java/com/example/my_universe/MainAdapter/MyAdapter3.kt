package com.example.my_universe.MainAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_universe.databinding.ItemImageBinding
import com.example.my_universe.recycler.ItemData

//뷰를 모아둔 박스 -> 목록 요소의 뷰,
class MyViewHolder3 (val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

// 뷰와 데이터 연결 한다.
// 리사이클러뷰에서 , 뷰페이저2에서도 같은 패턴으로 사용할 예정.
// 지금은 더미 데이터 :datas , 공공데이터 내지, 백에서 연결된 데이터
class MyAdapter3 (val context: Context, val datas: MutableList<ItemData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        binding.testText.text = datas[position].email
        Glide
            .with(context)
            .load(datas[position].img1)
            .override(300,200)


        // 더미 데이터 재사용, 일단, 텍스트 뷰
        val testDataSet = ArrayList<String>()
        testDataSet.add("https://firebasestorage.googleapis.com/v0/b/androidtest-8f15c.appspot.com/o/AndroidImageShareApp%2FojKcLdTEZ9fvYYUBE5Mz.jpg?alt=media&token=e2b4a543-856e-4cf3-affe-749340d37f4a")
        testDataSet.add(("https://firebasestorage.googleapis.com/v0/b/androidtest-8f15c.appspot.com/o/Cr5r47Ec1URLYg77rSZuPvnKGcy2.png?alt=media&token=7c534f3d-5eb5-43f5-8dde-d051664ee042"))
        //

        //viewPager2 넣기
        binding.tapFragSliderviewPager1.adapter = MyAdapter2(context,testDataSet)

//        binding.itemRoot.setOnClickListener {
//            Log.d("lsy", "item clicked : $position")
//        }
    }

}

// 목록 아이템의 요소 뷰를 만들기.