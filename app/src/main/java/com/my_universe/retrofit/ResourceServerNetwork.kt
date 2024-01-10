package com.my_universe.retrofit

import com.my_universe.model.RequestResultVO
import com.my_universe.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ResourceServerNetwork {
    @POST("user/register")
    fun doRegister(
        @Body user: User
    ): retrofit2.Call<RequestResultVO>
    @POST("user/login")
    fun doLogin(
        @Body user: User
    ): retrofit2.Call<RequestResultVO>
    @POST("user/auth")
    fun getAuthPage(

    ): retrofit2.Call<RequestResultVO>
}