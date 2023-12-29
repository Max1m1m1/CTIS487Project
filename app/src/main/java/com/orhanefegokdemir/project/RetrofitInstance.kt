package com.orhanefegokdemir.project
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {

   private const val BASE_URL = "https://jsonkeeper.com/b/" // Replace with your API base URL

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}