package com.donutsbite.godofmem.api

import com.donutsbite.godofmem.dto.LoginData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    companion object {
        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.30.1.30:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create<ApiService>(ApiService::class.java)
        }
    }

    @POST("/api/user/v1/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse
}