package com.example.my_universe.MainFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_universe.API.MyApplication
import com.example.my_universe.ApiModel.BoardListModel
import com.example.my_universe.MainAdapter.MyAdapter3
import com.example.my_universe.databinding.FragmentTest1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Test1Fragment : Fragment() {
lateinit var binding: FragmentTest1Binding
    val serviceKey = "%2BfUGybpr6srFj3hrh795f6tUPqEUPO7DG2CAnD%2B6adcNnaLMixQSsnK3MvqdvwFBHoed7QrJRZPQwYgXDGgKfA%3D%3D"
    var pageNum = 1
    var numOfRows = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTest1Binding.inflate(layoutInflater)

        // 이 데이터 들은 , 외부에서, 레트로핏 , 통신 라이브러리 이용해서, 데이터 받아와야함.

        val networkService = (activity?.applicationContext as MyApplication).networkService
        val boardListCall = networkService.getMainBoardItem(serviceKey, pageNum, numOfRows, "json")
        boardListCall.enqueue(object : Callback<BoardListModel> {
            override fun onResponse(
                call: Call<BoardListModel>,
                response: Response<BoardListModel>
            ) {
                val boardListModel = response.body()
                if (boardListModel != null) {
                    Log.d("API서버 데이터 확인", "BoardListModel 값 : ${boardListModel.item}")
                    binding.tabRecyclerTest1.adapter =
                        context?.let { MyAdapter3(it, boardListModel.item) }
                    binding.tabRecyclerTest1.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<BoardListModel>, t: Throwable) {
                Log.d("데이터 전송 실패", "데이터를 못 받아옴")
                call.cancel()
            }
        })
    }
}