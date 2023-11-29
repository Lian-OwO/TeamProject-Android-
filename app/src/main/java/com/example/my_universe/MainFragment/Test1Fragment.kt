package com.example.my_universe.MainFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_universe.API.MyApplication
import com.example.my_universe.ApiModel.BoardListModel
import com.example.my_universe.ApiModel.ItemListModel
import com.example.my_universe.ApiModel.ItemListModel77
import com.example.my_universe.MainAdapter.MyAdapter3
import com.example.my_universe.databinding.FragmentTest1Binding


import com.example.my_universe.recycler.ItemData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Test1Fragment : Fragment() {
lateinit var binding: FragmentTest1Binding
    val serviceKey2 = "kg65cAU1UMyBFZ6ROiERqjqaGt2Ntxv+j96pqyIGsQ+fbnuGPuRs7ZQmr6MhZMqV/6ZmaAiKEQ5y+S0IOTO+bg=="
    var pageNum = 1
    var numOfRows = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTest1Binding.inflate(layoutInflater)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding = FragmentTest1Binding.inflate(layoutInflater)
        binding = FragmentTest1Binding.inflate(layoutInflater,container,false)
        // 이 데이터 들은 , 외부에서, 레트로핏 , 통신 라이브러리 이용해서, 데이터 받아와야함.

        val networkService = (activity?.applicationContext as com.example.my_universe.MyApplication).networkService
        val boardListCall = networkService.getMainBoardItem(serviceKey2, pageNum, numOfRows, "json")
        boardListCall.enqueue(object : Callback<ItemListModel77> {
            override fun onResponse(
                call: Call<ItemListModel77>,
                response: Response<ItemListModel77>
            ) {
                val boardListModel = response.body()
                if (boardListModel != null) {
                    Log.d("lsy", "BoardListModel 값 : ${boardListModel.getRecommendedKr.item}")
                    binding.tabRecyclerTest1.adapter =
                        context?.let { boardListModel.getRecommendedKr.item?.let { it1 ->
                            MyAdapter3(it,
                                it1
                            )
                        } }
                    binding.tabRecyclerTest1.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<ItemListModel77>, t: Throwable) {
                Log.d("lsy", "데이터를 못 받아옴")
                call.cancel()
            }
        })

        return binding.root
    }

}

