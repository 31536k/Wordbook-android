package com.donutsbite.godofmem.feature.login.data

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val email: String,
        val displayName: String
)