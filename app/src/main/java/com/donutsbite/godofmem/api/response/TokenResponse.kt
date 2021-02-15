package com.donutsbite.godofmem.api.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)