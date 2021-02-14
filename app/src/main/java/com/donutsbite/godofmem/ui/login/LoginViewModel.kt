package com.donutsbite.godofmem.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiError
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.dto.LoginData
import com.donutsbite.godofmem.util.ToastUtil
import com.facebook.stetho.server.http.HttpStatus
import retrofit2.HttpException

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {

        ApiLauncher.launchMain(
            { ApiService.instance.login(LoginData(email, password)) },
            { response ->
                _loginResult.value = LoginResult(success = LoggedInUserView(displayName = response.name))
            },
            {
                error ->
                if (error.code == ApiError.BAD_REQUEST) {
                    _loginResult.value = LoginResult(error = R.string.login_bad_request)
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }

            }
        )

        /*
        val loginCall = apiService.login(LoginData(email, password))
        loginCall.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                response.body()?.let {
                    Result.Success(LoggedInUser(email, it.name))
                    _loginResult.value = LoginResult(success = LoggedInUserView(displayName = it.name))
                }
            }
        })
         */
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder email validation check
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}