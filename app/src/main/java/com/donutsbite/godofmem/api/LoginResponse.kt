package com.donutsbite.godofmem.api

data class LoginResponse(
    val name: String,
    val accessToken: String,
    val refreshToken: String
)