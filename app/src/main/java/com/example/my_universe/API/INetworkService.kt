package com.example.my_universe.API

import com.example.my_universe.ApiModel.BoardListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkService {
    @GET("/getRecommendedKr")
    fun getMainBoardItem(
        @Query("serviceKey") serviceKey: String?,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("resultType") resultType : String
    ): Call<BoardListModel>
}
