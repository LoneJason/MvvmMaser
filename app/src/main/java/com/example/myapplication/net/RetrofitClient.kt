package com.example.myapplication.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor() {

    companion object {
        var instance = RetrofitClient()
    }

    /**
     * 这里可以添加拦截器、缓存策略等等操作
     */
    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .connectTimeout(3000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
        builder.build()
    }

    open fun <T> getService(serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("xxxx")
            .build()
            .create(serviceClass)
    }
}