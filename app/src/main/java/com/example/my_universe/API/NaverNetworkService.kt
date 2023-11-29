package com.example.my_universe.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverNetworkService {
    //    https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=128.12345,37.98776&output=json
    //    @GET("/naveropenapi.apigw.ntruss.com/")
    @GET("/map-reversegeocode/v2/gc")
    @Headers(
        "X-NCP-APIGW-API-KEY-ID: ymmw0z4x21",
        "X-NCP-APIGW-API-KEY: QDwbr8N7oNoYREWskANsBtNt5Ytbt8SBVg5wrjP6"
    )
    fun reverseGeocode(
        @Query("coords") coords: String?,
        @Query("output") output: String = "json"

    ): Call<Unit>
}