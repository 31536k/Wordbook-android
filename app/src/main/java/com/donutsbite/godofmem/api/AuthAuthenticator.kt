package com.donutsbite.godofmem.api

import com.donutsbite.godofmem.api.dto.TokenData
import com.donutsbite.godofmem.util.LocalSettings
import com.orhanobut.logger.Logger
import okhttp3.*

class ApiRequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().signedRequest())
    }
}

private fun Request.signedRequest(): Request {
    val accessToken = LocalSettings.instance.getAccessToken()
    return newBuilder()
        .addHeader("Authorization", "Bearer $accessToken")
        .build()
}

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
        val tokenResponse = ApiService.instance.refreshToken(tokenData).execute().body()
        tokenResponse?.let {
            LocalSettings.instance.setTokens(it.accessToken, it.refreshToken)
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