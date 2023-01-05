package com.chuanyunglin.android.kotlin.retrofit2

import android.os.Bundle
import android.util.Log
import com.chuanyunglin.android.kotlin.BaseAppCompatActivity
import com.chuanyunglin.android.kotlin.databinding.ActivityRetrofit2TestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Retrofit2TestActivity : BaseAppCompatActivity() {
    companion object {
        private val TAG = Retrofit2TestActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityRetrofit2TestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRetrofit2TestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getVisitorInfoUsingQueryMap()
    }

    private fun getVisitorInfo() {
        KApiManager.api.getVisitorInfo("774740085").enqueue(object: Callback<VisitorInfo> {
            override fun onResponse(call: Call<VisitorInfo>, response: Response<VisitorInfo>) {
                Log.w(TAG, "onResponse, code = ${response.code()}, body = ${response.body()}")
            }

            override fun onFailure(call: Call<VisitorInfo>, t: Throwable) {
                Log.e(TAG, "onFailure = $t,")
            }
        })
    }

    private fun getVisitorInfoUsingQueryMap() {
        val map = HashMap<String, Any>()
        map["skey"] = "774740085"
        KApiManager.api.getVisitorInfo(map).enqueue(object: Callback<VisitorInfo> {
            override fun onResponse(call: Call<VisitorInfo>, response: Response<VisitorInfo>) {
                Log.w(TAG, "onResponse, code = ${response.code()}, body = ${response.body()}")
            }

            override fun onFailure(call: Call<VisitorInfo>, t: Throwable) {
                Log.e(TAG, "onFailure = $t,")
            }
        })
    }
}