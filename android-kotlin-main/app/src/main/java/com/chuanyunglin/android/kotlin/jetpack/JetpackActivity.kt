package com.chuanyunglin.android.kotlin.jetpack

import android.content.Intent
import com.chuanyunglin.android.kotlin.RecyclerViewActivity
import com.chuanyunglin.android.kotlin.jetpack.hilt.HiltActivity
import com.chuanyunglin.android.kotlin.jetpack.livedata.LiveDataActivity

class JetpackActivity : RecyclerViewActivity() {
    override fun generateAllActivities(): List<Pair<String, Intent>> {
        return arrayListOf(
            generatePair("LiveData", LiveDataActivity::class.java),
            generatePair("Hilt", HiltActivity::class.java)
        )
    }
}