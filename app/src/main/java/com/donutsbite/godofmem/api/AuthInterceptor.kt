package com.donutsbite.godofmem.api

import com.donutsbite.godofmem.api.dto.TokenData
import com.donutsbite.godofmem.api.module.ApiError
import com.donutsbite.godofmem.util.LocalSettings
import com.facebook.stetho.server.http.HttpStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiRequestInterceptor: Interceptor {
    private var tryCount = 0
    override fun intercept(chain: Interceptor.Chain): Response {
        val orgRequest = chain.request()
        val response = chain.proceed(orgRequest.signedRequest())
        return if (response.code() == ApiError.UNAUTHORIZED) {
            val tokenRefreshed = this.requestRefreshToken(3)
            if (tokenRefreshed) {
                chain.proceed(orgRequest.signedRequest())
            } else {
                // 토큰 갱신 실패
                // TODO 로그아웃 상태로 전환
                LocalSettings.instance.removeTokens()
                response
            }
        } else {
            response
        }
    }

    private fun requestRefreshToken(totalTryCount: Int): Boolean {
        tryCount = 0

        return runBlocking {
            var status = 0
            // 성공하거나 403 이면 retry 하지 않는다
            while(status != HttpStatus.HTTP_OK && status != ApiError.FORBIDDEN && tryCount < totalTryCount) {
                delay(2000L * tryCount)
                tryCount++
                status = refreshToken()
            }

            status == HttpStatus.HTTP_OK
        }
    }

    private fun refreshToken(): Int {
        var statusCode = 0
        try {
            val tokenData = TokenData(
                LocalSettings.instance.getAccessToken(),
                LocalSettings.instance.getRefreshToken()
            )

            val response = ApiService.instance.refreshToken(tokenData).execute()
            val tokenResponse = response.body()
            tokenResponse?.let {
                LocalSettings.instance.setTokens(it.accessToken, it.refreshToken)
            }

            statusCode = response.code()
        } catch (e: IOException) {
        }

        return statusCode
    }

    private fun Request.signedRequest(): Request {
        val accessToken = LocalSettings.instance.getAccessToken()
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