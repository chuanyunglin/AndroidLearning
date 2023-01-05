package com.chuanyunglin.android.kotlin

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.chuanyunglin.android.kotlin.coroutine.CoroutineTestActivity
import com.chuanyunglin.android.kotlin.jetpack.JetpackActivity
import com.chuanyunglin.android.kotlin.mqtt.MqttTestActivity
import com.chuanyunglin.android.kotlin.retrofit2.Retrofit2TestActivity

open class MainViewModel(application: Application) : AndroidViewModel(application) {

    open fun generateAllActivities(): List<Pair<String, Intent>> {
        return arrayListOf(
            generatePair("Coroutine Test", CoroutineTestActivity::class.java),
            generatePair("Jetpack", JetpackActivity::class.java),
            generatePair("Retrofit2 Test", Retrofit2TestActivity::class.java),
            generatePair("MQtt Test", MqttTestActivity::class.java)
        )
    }

    protected fun generatePair(title: String, cls: Class<*>) : Pair<String, Intent> {
        val intent = Intent(getApplication(), cls)
        intent.putExtra("title", title)
        return Pair(title, intent)
    }
}