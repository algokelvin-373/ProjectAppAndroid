package com.algokelvin.loginplay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.algokelvin.loginplay.api.ApiClient
import com.algokelvin.loginplay.api.ApiEndpoint
import com.algokelvin.loginplay.api.request.LoginRequest
import com.algokelvin.loginplay.api.response.LoginResponse
import com.algokelvin.loginplay.utils.GlobalFunction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    fun validateEmail(email: String): Boolean {
        return GlobalFunction.validateEmail(email)
    }

    fun validatePassword(password: String): Boolean {
        return GlobalFunction.validatePassword(password)
    }

    fun doLogin(request: LoginRequest): LiveData<Result<LoginResponse?>> {
        val liveDataResponse = MutableLiveData<Result<LoginResponse?>>()
        val apiEndpoint = ApiClient.getApiInstance().create(ApiEndpoint::class.java)
        val call = apiEndpoint.login(request)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    liveDataResponse.postValue(Result.success(response.body()))
                } else {
                    liveDataResponse.postValue(Result.failure(Throwable("Login failed with error code: ${response.code()}")))
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                liveDataResponse.postValue(Result.failure(t))
            }
        })
        return liveDataResponse
    }

}