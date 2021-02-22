package com.donutsbite.godofmem.domain

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val email: String,
        val displayName: String
)