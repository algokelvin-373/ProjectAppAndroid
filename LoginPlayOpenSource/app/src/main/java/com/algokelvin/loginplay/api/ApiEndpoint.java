package com.algokelvin.loginplay.api;

import com.algokelvin.loginplay.api.request.LoginRequest;
import com.algokelvin.loginplay.api.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEndpoint {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
