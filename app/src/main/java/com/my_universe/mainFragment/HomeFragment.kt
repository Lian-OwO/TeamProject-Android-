package com.my_universe.mainFragment

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my_universe.apiModel.ItemListModel77
import com.my_universe.mainAdapter.MyAdapter3
import com.my_universe.databinding.FragmentHomeBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var myAdapter: MyAdapter3
    lateinit var binding: FragmentHomeBinding
    val serviceKey2 =
        "kg65cAU1UMyBFZ6ROiERqjqaGt2Ntxv+j96pqyIGsQ+fbnuGPuRs7ZQmr6MhZMqV/6ZmaAiKEQ5y+S0IOTO+bg=="
    var pageNum = 1
    var numOfRows = 10

    private var isLoading = false
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        myAdapter = MyAdapter3(requireContext(), mutableListOf())
        binding.tabRecyclerTest1.adapter = myAdapter
        binding.tabRecyclerTest1.layoutManager = LinearLayoutManager(requireContext())



        val networkService =
            (activity?.applicationContext as com.my_universe.MyApplication).networkService
        

        val boardListCall = networkService.getMainBoardItem(serviceKey2, pageNum, numOfRows, "json")
        boardListCall.enqueue(object : Callback<ItemListModel77> {
            override fun onResponse(
                call: Call<ItemListModel77>,
                response: Response<ItemListModel77>
            ) {
                val boardListModel = response.body()
                if (boardListModel != null) {
                    Log.d("lsy", "BoardListModel 값 : ${boardListModel.getRecommendedKr.item}")

                    // 어댑터에 새로운 데이터 설정
                    boardListModel.getRecommendedKr.item?.let { myAdapter.setData(it) }
                    binding.tabRecyclerTest1.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<ItemListModel77>, t: Throwable) {
                Log.d("lsy", "데이터를 못 받아옴")
                call.cancel()
            }
        })

        binding.tabRecyclerTest1.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE
                    ) {
                        loadMoreItems(networkService)
                    }
                }
            }
        })
        return binding.root
    }



    private fun loadMoreItems(networkService: com.my_universe.api.INetworkService) {
        isLoading = true
        pageNum++

        val boardListCall =
            networkService.getMainBoardItem(serviceKey2, pageNum, numOfRows, "json")
        boardListCall.enqueue(object : Callback<ItemListModel77> {
            override fun onResponse(
                call: Call<ItemListModel77>,
                response: Response<ItemListModel77>
            ) {
                val boardListModel = response.body()
                if (boardListModel != null) {
                    val newData = boardListModel.getRecommendedKr.item
                    if (newData.isNullOrEmpty()) {
                        // 만약 불러올 페이지가 없다면 종료
                        isLastPage = true
                    } else {
                        // 어댑터에 새로운 데이터 추가
                        myAdapter.addData(newData)
                        myAdapter.notifyDataSetChanged()
                    }
                    isLoading = false
                }
            }

            override fun onFailure(call: Call<ItemListModel77>, t: Throwable) {
                Log.d("smh", "데이터를 못받아옴")
                call.cancel()
                isLoading = false
            }
        })
    }
}
