package com.yablunin.shopnstock.data.clients

import com.yablunin.shopnstock.data.services.FlagsApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFlagsClient {
    private const val URL = "https://flagsapi.com"
    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val flagsApiService: FlagsApiService by lazy {
        retrofit.create(FlagsApiService::class.java)
    }
}