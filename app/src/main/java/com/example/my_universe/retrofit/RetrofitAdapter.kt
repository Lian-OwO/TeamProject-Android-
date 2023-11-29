package com.example.my_universe.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAdapter {
    val BASE_URL : String = "http://10.100.104.53:80"
    var testService: TestNetwork

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    init {
        testService = retrofit.create(TestNetwork::class.java)
    }
}