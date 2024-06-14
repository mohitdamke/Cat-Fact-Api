package com.example.catfactsmvvm.data.api


import com.example.catfactsmvvm.domain.model.CatList
import retrofit2.Response
import retrofit2.http.GET

interface CatApi {

    @GET("fact")
    suspend fun getCatResult():Response<CatList>

    companion object{
        const val BASE_URL = "https://catfact.ninja/"
    }
}