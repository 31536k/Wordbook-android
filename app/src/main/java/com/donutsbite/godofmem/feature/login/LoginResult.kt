package com.donutsbite.godofmem.feature.login

import com.donutsbite.godofmem.feature.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)