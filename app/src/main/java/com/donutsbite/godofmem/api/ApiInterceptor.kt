package com.donutsbite.godofmem.api

import com.donutsbite.godofmem.util.LocalSettings
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = LocalSettings.instance.getAccessToken()
        return if (!accessToken.isNullOrBlank()) {
            val bearerToken = "Bearer $accessToken"
            val authAddedRequest = chain.request().newBuilder().addHeader("Authorization", bearerToken).build()
            chain.proceed(authAddedRequest)
        } else {
            chain.proceed(chain.request())
        }
    }
}