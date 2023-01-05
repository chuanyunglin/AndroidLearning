package com.chuanyunglin.android.kotlin.retrofit2

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface KApi {
    @POST("visitor.info")
    fun getVisitorInfo(@Query("skey") skey: String): Call<VisitorInfo>

    @POST("visitor.info")
    fun getVisitorInfo(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<VisitorInfo>
}