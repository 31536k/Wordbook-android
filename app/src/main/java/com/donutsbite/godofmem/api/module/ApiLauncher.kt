package com.donutsbite.godofmem.api.module

import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object ApiLauncher {
    const val SUCCESS = 0
    private val scope = CoroutineScope(Dispatchers.Main)

    fun <T : Any> launchMain(
        api: suspend () -> T,
        successBlock: suspend (T) -> Unit,
        failBlock: (suspend (ApiError) -> Unit)? = null,
        finishBlock: (suspend () -> Unit)? = null,
        showServerErrorToast: Boolean = true
    ): Job {
        return scope.launch {
            val response = try {
                api.invoke()
            } catch (e: Exception) {
                onFailure(failBlock, e, showServerErrorToast)
                null
            }

            response?.let {
                try {
                    successBlock.invoke(it)
                } catch (e: Exception) {
                    onFailure(failBlock, e, showServerErrorToast)
                }
            }

            try {
                finishBlock?.invoke()
            } catch (ignore: Exception) {
                Logger.e(ignore.message ?: "")
            }
        }
    }

    private suspend fun onFailure(
        failBlock: (suspend (ApiError) -> Unit)? = null,
        e: Exception,
        showServerErrorToast: Boolean
    ) {
        Logger.e(e.message ?: "")
        try {
            val apiError = ApiErrorHandler.createErrorFromException(e, showServerErrorToast)
            failBlock?.invoke(apiError)
        } catch (ignore: Exception) {
            Logger.e(ignore.message ?: "")
        }
    }
}