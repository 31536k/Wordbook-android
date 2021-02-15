package com.donutsbite.godofmem.api

import com.donutsbite.godofmem.api.dto.TokenData
import com.donutsbite.godofmem.api.module.ApiError
import com.donutsbite.godofmem.util.LocalSettings
import com.orhanobut.logger.Logger
import okhttp3.*

class ApiRequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val orgRequest = chain.request()
        val response = chain.proceed(orgRequest.signedRequest())
        if (response.code() == ApiError.UNAUTHORIZED) {
            val tokenData = TokenData(
                LocalSettings.instance.getAccessToken(),
                LocalSettings.instance.getRefreshToken()
            )
            val tokenResponse = ApiService.instance.refreshToken(tokenData).execute().body()
            return if (tokenResponse != null) {
                LocalSettings.instance.setTokens(tokenResponse.accessToken, tokenResponse.refreshToken)
                chain.proceed(orgRequest.signedRequest())
            } else {
                // 토큰 갱신 실패
                // 로그인 화면으로 이동한다. Event 전송
                response
            }
        } else {
            return response
        }
    }

    private fun Request.signedRequest(): Request {
        val accessToken = LocalSettings.instance.getAccessToken()
        Logger.i("1_______________ request with $accessToken")
        return newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
    }
}

/* 아래 방식을 쓰면 ApiService.instance.refreshToken(tokenData).execute().body() 부분이 두번 호출된다
class TokenRefreshAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? = when {
        response.retryCount > 2 -> null
        else -> response.refreshTokenAndContinue()
    }

    private fun Response.refreshTokenAndContinue(): Request? = try {
        val tokenData = TokenData(
            LocalSettings.instance.getAccessToken(),
            LocalSettings.instance.getRefreshToken()
        )
        Logger.i("2_______________ request refreshToken ${tokenData.accessToken} \n ${tokenData.refreshToken}")
        val tokenResponse = ApiService.instance.refreshToken(tokenData).execute().body()
        tokenResponse?.let {
            Logger.i("3_______________ save tokens ${it.accessToken} \n ${it.refreshToken}")
            LocalSettings.instance.setTokens(it.accessToken, it.refreshToken)
            Logger.i("4_______________ continue request")
            request().signedRequest()
        }
    } catch (e: Throwable) {
        Logger.e(e.message ?: "")
        null
    }

    private val Response.retryCount: Int
        get() {
            var currentResponse = priorResponse()
            var result = 0
            while (currentResponse != null) {
                result++
                currentResponse = currentResponse.priorResponse()
            }
            return result
        }
}

 */