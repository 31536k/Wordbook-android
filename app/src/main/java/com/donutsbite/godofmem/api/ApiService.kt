package com.donutsbite.godofmem.api

import com.donutsbite.godofmem.api.response.LoginResponse
import com.donutsbite.godofmem.api.dto.LoginData
import com.donutsbite.godofmem.api.response.BookListResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    companion object {
        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.30.1.30:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(ApiInterceptor())
                        .build()
                )
                .build()
            retrofit.create<ApiService>(ApiService::class.java)
        }
    }

    @POST("/api/user/v1/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse

    @GET("/api/book/v1/books")
    suspend fun getBookList(): BookListResponse
}