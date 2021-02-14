package com.donutsbite.godofmem.api.module

class ApiError(val code: Int, val message: String) {
    override fun toString(): String {
        return String.format("status code %d message %s", code, message)
    }

    companion object {
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val INTERNAL_ERROR = -999
        const val MAINTENANCE_ERROR = -800
        const val NETWORK_ERROR = -600
        const val SERVER_ERROR = -500
        const val NEED_UPDATE_ERROR = -403
    }
}