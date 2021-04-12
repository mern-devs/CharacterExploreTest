package com.characterexploretest.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val mainUrl = "https://breakingbadapi.com/api/"
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(300, TimeUnit.SECONDS)
        .connectTimeout(300, TimeUnit.SECONDS)
        .build()
    private val retrofitApi: Retrofit = Retrofit.Builder()
        .baseUrl(mainUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val api: Api = retrofitApi.create(Api::class.java)
}