package com.algokelvin.loginplay.api;

import com.algokelvin.loginplay.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit api;

    public static Retrofit getApiInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return api;
    }

    public static Retrofit getApiInstanceWithCookie(String tokenAccess, String tokenRefresh) {
        if (api == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        //.header("Cookie", "accessToken="+tokenAccess+";refreshToken="+tokenRefresh)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            });

            api = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return api;
    }
}
