package com.example.catfactsmvvm.di

import com.example.catfactsmvvm.data.api.CatApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    val api: CatApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CatApi.BASE_URL)
        .build()
        .create(CatApi::class.java)
}
