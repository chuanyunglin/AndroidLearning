package com.chuanyunglin.android.kotlin.jetpack.livedata

import android.os.Bundle
import com.chuanyunglin.android.kotlin.BaseAppCompatActivity
import com.chuanyunglin.android.kotlin.databinding.ActivityLiveDataBinding

class LiveDataActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityLiveDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}