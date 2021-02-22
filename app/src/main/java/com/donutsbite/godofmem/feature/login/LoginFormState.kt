package com.donutsbite.godofmem.feature.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(val emailError: Int? = null,
                          val passwordError: Int? = null,
                          val isDataValid: Boolean = false)