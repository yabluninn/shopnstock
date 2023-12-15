package com.yablunin.shopnstock.data.services

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FlagsApiService {
    @GET("/{country_code}/flat/64.png")
    suspend fun getFlagImage(@Path("country_code") countryCode: String): Response<ResponseBody>
}