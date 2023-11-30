package com.example.my_universe.retrofit

import retrofit2.http.GET
import retrofit2.http.Header

interface ResourceServerNetwork {
    @GET("/board/new")
    fun getBoardUpload(
        @Header("Authorization") accessToken: String
    ): retrofit2.Call<String>
}