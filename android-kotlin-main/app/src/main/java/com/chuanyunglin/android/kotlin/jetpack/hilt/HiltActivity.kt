package com.chuanyunglin.android.kotlin.jetpack.hilt

import android.os.Bundle
import com.chuanyunglin.android.kotlin.BaseAppCompatActivity
import com.chuanyunglin.android.kotlin.databinding.ActivityHiltBinding
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class HiltActivity : BaseAppCompatActivity() {
    private lateinit var binding: ActivityHiltBinding
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding = ActivityHiltBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}