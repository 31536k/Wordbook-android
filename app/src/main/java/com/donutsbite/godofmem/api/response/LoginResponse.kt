package com.donutsbite.godofmem.api.response

data class LoginResponse(
    val name: String,
    val accessToken: String,
    val refreshToken: String
)