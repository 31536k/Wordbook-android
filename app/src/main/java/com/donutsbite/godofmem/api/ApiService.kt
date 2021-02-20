package com.donutsbite.godofmem.api

import com.donutsbite.godofmem.api.dto.LoginData
import com.donutsbite.godofmem.api.dto.QuizResultData
import com.donutsbite.godofmem.api.dto.TokenData
import com.donutsbite.godofmem.api.response.*
import okhttp3.OkHttpClient
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
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(ApiRequestInterceptor())
                        .build()
                )
                .build()
            retrofit.create<ApiService>(ApiService::class.java)
        }
    }

    @POST("/api/user/v1/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse

    @POST("/api/user/v1/refresh-token")
    fun refreshToken(@Body tokenData: TokenData): Call<TokenResponse>

    @GET("/api/book/v1/books")
    suspend fun getBookList(): BookListResponse

    @GET("/api/chapter/v1/chapters")
    suspend fun getChaptersOfBook(@Query("bookid") bookId: Long): ChapterListResponse

    @GET("/api/question/v1/questions")
    suspend fun getQuestionsInChapter(@Query("chapterid") chapterId: Long): QuestionListInChapterResponse

    @GET("/api/quiz/v1/result/{chapterId}")
    suspend fun getQuizResult(@Path("chapterId") chapterId: Long): QuizResultResponse

    @POST("/api/quiz/v1/result")
    suspend fun saveQuizResult(@Body quizResultData: QuizResultData): QuizResultSaveResponse
}

