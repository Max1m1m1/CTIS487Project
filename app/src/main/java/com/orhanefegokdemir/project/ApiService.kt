package com.orhanefegokdemir.project
import retrofit2.Call


import retrofit2.http.GET
import com.orhanefegokdemir.project.syste.Supplement
interface ApiService {
    @GET("9PB0")

    fun getData(): Call<List<Supplement>>

}