package com.example.my_universe.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TestNetwork {

    @GET("/test")
    fun getTest(
        @Header("Authorization") accessToken: String,
        @Header("User-Info") userInfo: String
    ): retrofit2.Call<Map<String, String>>
}