package com.donutsbite.godofmem.api.module

import com.donutsbite.godofmem.App
import com.donutsbite.godofmem.BuildConfig
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.util.ToastUtil
import retrofit2.HttpException

object ApiErrorHandler {
    fun createErrorFromException(e: Exception, showServerErrorToast: Boolean): ApiError {
        return when (e) {
            is ServerError -> createError(ApiError.NETWORK_ERROR, if (BuildConfig.DEBUG) e.errorBody else "", showServerErrorToast)
            is HttpException -> createError(e.code(), e.message(), showServerErrorToast)
            else -> createError(ApiError.NETWORK_ERROR, "", showServerErrorToast)
        }
    }

    private fun createError(errorCode: Int, serverMessage: String?, showServerErrorToast: Boolean): ApiError {
        var clientMessage: String?

        if (!serverMessage.isNullOrBlank()) {
            clientMessage = serverMessage
            if (showServerErrorToast) {
                // 일단 현재는 필요없어 보여서 Server Error 를 띄울 필요가 발생하면 그 때 고민
//                ToastUtil.show(serverMessage) // ToastUtil 에서 main thread로 전환하고 있으므로 thread context는 신경쓰지 않아도 됨
            }
        } else {
            clientMessage = App.getApp().getString(R.string.emoticon_network_error_msg)
        }

        return ApiError(errorCode, clientMessage)
    }
}