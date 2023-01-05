package com.chuanyunglin.android.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

open class BaseAppCompatActivity : AppCompatActivity() {
    override fun getIntent(): Intent {
        val intent = super.getIntent()
        intent.getStringExtra("title") ?.run {
            title = this
        }
        return intent
    }
}