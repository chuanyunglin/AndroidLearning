package com.chuanyunglin.android.kotlin.retrofit2

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KApiManager {
    private val TAG = KApiManager::class.java.simpleName
    lateinit var api: KApi

    init {
        generateApi()
    }

    private fun generateApi() {
        api = Retrofit
            .Builder()
            .baseUrl("https://api.uomg.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(KApi::class.java)
    }
}