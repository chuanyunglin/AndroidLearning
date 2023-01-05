package com.chuanyunglin.android.kotlin.mqtt

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.collection.ArraySet
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttClient

class MqttViewModel(application: Application) : AndroidViewModel(application) {
    val subscribedTopics = MutableLiveData<ArraySet<String>>()
    private lateinit var mqttClient: MqttClient
    private val sharedPreferences : SharedPreferences = application.getSharedPreferences("mqtt", Context.MODE_PRIVATE)

    fun mqttConnect() {
        viewModelScope.launch {

        }
    }

    fun loadSubscribedTopics() {
        val topics = sharedPreferences.getStringSet("topics", ArraySet<String>()) as ArraySet<String>
        subscribedTopics.postValue(topics)
    }

    fun saveSubscribedTopics(topics: Set<String>) {
        sharedPreferences.edit().putStringSet("topics", topics)
    }
}