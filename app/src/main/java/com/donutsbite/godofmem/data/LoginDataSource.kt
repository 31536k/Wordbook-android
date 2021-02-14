package com.donutsbite.godofmem.data

import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.LoginResponse
import com.donutsbite.godofmem.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource() {
    fun login(email: String, password: String): Result<LoggedInUser> {

        /*
        val loginCall = apiService.login(email, password)

        loginCall.execute().body()
        loginCall.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Result.Error(IOException("Error logging in", t))
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val successResult = response.body()!!.let {
                    Result.Success(LoggedInUser(email, it.name))
                }
            }
        })

         */

        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}