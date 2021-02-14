package com.donutsbite.godofmem.api.module

import java.io.IOException

class ServerError : IOException {

    var code = 0
        private set

    var errorBody: String? = null
        private set

    constructor(message: String?) : super(message)

    constructor(code: Int, body: String?): super(body) {
        this.code = code
        this.errorBody = body
    }
}