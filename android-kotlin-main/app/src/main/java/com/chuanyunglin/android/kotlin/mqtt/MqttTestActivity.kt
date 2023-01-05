package com.chuanyunglin.android.kotlin.mqtt

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.collection.ArraySet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chuanyunglin.android.kotlin.BaseAppCompatActivity
import com.chuanyunglin.android.kotlin.databinding.ActivityMqttTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.random.Random


class MqttTestActivity : BaseAppCompatActivity() , View.OnClickListener {
    companion object {
        private val TAG = MqttTestActivity::class.java.simpleName
        private val QOS = listOf("Qos 0", "Qos 1", "Qos 2")
    }

    private lateinit var binding: ActivityMqttTestBinding
    private lateinit var mqttViewModel: MqttViewModel
    private var qos : Set<String>? = null
    private var topics: ArraySet<String>? = null
    private var mqttClient: MqttAndroidClient? = null
    private var connected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMqttTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mqttViewModel = ViewModelProvider(this)[MqttViewModel::class.java]

        setupViews()
        loadSubscribedTopics()

    }

    private fun setupViews() {
        binding.btnMqttConnect.setOnClickListener(this)
        binding.btnMqttDisconnect.setOnClickListener(this)
        binding.btnPublish.setOnClickListener(this)
        binding.btnSubscribeTopic.setOnClickListener(this)
        binding.btnUnsubscribeTopic.setOnClickListener(this)

        // qos
        binding.spPublishQos.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, QOS)

        // topics
        binding.rvTopics.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        binding.rvTopics.adapter = MqttTopicRecyclerViewAdaptor(topics)

        mqttViewModel.subscribedTopics.observe(this, object: Observer<ArraySet<String>> {
            override fun onChanged(t: ArraySet<String>?) {
                Toast.makeText(this@MqttTestActivity, "topics = ${t?.joinToString()}", Toast.LENGTH_SHORT).show()
                topics = t
                updateTopicsList(t)
            }
        })
    }

    private fun updateTopicsList(topics: ArraySet<String>?) {
        (binding.rvTopics.adapter as MqttTopicRecyclerViewAdaptor).setTopics(topics)
    }

    private fun loadSubscribedTopics() {
        lifecycleScope.launch {
            mqttViewModel.loadSubscribedTopics()
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnMqttConnect -> onClickMqttConnect()
            binding.btnMqttDisconnect -> onClickMqttDisconnect()
            binding.btnPublish -> onClickPublish()
            binding.btnSubscribeTopic -> onClickSubscribe()
            binding.btnUnsubscribeTopic -> onClickUnsubscribe()
        }
    }

    private fun onClickMqttConnect() {
        if (!connected) {
            var port = binding.etMqttBrokerPort.editableText.toString()
            if (port.isEmpty()) {
                port = binding.etMqttBrokerPort.hint.toString()
            }

            var url = binding.etMqttBrokerUrl.editableText.toString()
            if (url.isEmpty()) {
                url = binding.etMqttBrokerUrl.hint.toString()
            }

            url = "$url:$port"

            // url = "tcp://broker.hivemq.com:1883"
            // url = "ws://broker.hivemq.com:8000" // websocket


            if (mqttClient == null) {
                val clientId = Random(System.currentTimeMillis()).nextLong().toString()
                val mqttConnectOptions = MqttConnectOptions().apply {
                    connectionTimeout = 30; // second
                    keepAliveInterval = 300 // second
                    isCleanSession = false
                    isAutomaticReconnect = true
                    // userName = "chuanyung"
                    // password = "0000".toCharArray()
                }

                val mqttCallback = object: MqttCallback {
                    override fun connectionLost(cause: Throwable?) {
                        Log.w(TAG, "MqttCallback connectionLost: cause = ${cause?.message}")
                        connected = false
                        updateStatus("斷線 ${cause?.message}")
                    }

                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        Log.w(TAG, "MqttCallback messageArrived: topic = $topic, message = $message")
                        showToast("topic: $topic, message = $message")
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {
                        Log.w(TAG, "MqttCallback deliveryComplete: token = $token")
                    }
                }

                mqttClient = MqttAndroidClient(this, url, clientId).apply {
                    setCallback(mqttCallback)
                }

                mqttClient!!.connect(mqttConnectOptions, null, object: IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.w(TAG, "connect-> onSuccess: asyncActionToken = $asyncActionToken")
                        connected = true
                        updateStatus("連線成功")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.e(TAG, "connect-> onFailure: asyncActionToken = $asyncActionToken, exception = ${exception?.message}")
                        connected = false
                        updateStatus("連線失敗 ${exception?.message}")
                    }
                })
            }
        }
    }

    private fun onClickMqttDisconnect() {
        if (connected && mqttClient != null) {
            mqttClient!!.disconnect(DateUtils.SECOND_IN_MILLIS * 30, null, object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.w(TAG, "disconnect-> onSuccess: asyncActionToken = $asyncActionToken")
                    connected = false
                    mqttClient = null
                    updateStatus("斷線成功")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "disconnect-> onFailure: asyncActionToken = $asyncActionToken, exception = ${exception?.message}")
                    updateStatus("斷線失敗 ${exception?.message}")
                }
            })
        }
    }

    private fun onClickPublish() {
        val topic = binding.etPublishTopic.text.toString()
        if (topic.isNotEmpty()) {
            if (connected && mqttClient != null) {
                val mqttMessage = MqttMessage().apply {
                    qos = binding.spPublishQos.selectedItemPosition
                    payload = binding.etPublishMessage.text.toString().toByteArray()
                    isRetained = binding.cbPublishRetained.isChecked
                }
                mqttClient!!.publish(topic, mqttMessage, null, object: IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.w(TAG, "publish action -> onSuccess: asyncActionToken = $asyncActionToken")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.e(TAG, "publish action -> onFailure: asyncActionToken = $asyncActionToken, exception = ${exception?.message}")
                    }
                })
            }
        }
    }

    private fun onClickSubscribe() {
        val topic = binding.etSubscribeTopic.text.toString()
        if (topic.isNotEmpty()) {
            if (connected && mqttClient != null) {
//                if (topics == null) {
//                    topics = ArraySet<String>()
//                }

                // if (!topics!!.contains(topic)) {
                    val qos = binding.spPublishQos.selectedItemPosition
                    mqttClient!!.subscribe(topic, qos, null, object: IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            Log.w(TAG, "subscribe action -> onSuccess: asyncActionToken = $asyncActionToken")
//                            topics!!.add(topic)
//                            MainScope().launch {
//                                (binding.rvTopics.adapter as MqttTopicRecyclerViewAdaptor).setTopics(
//                                    topics
//                                )
//                                launch(Dispatchers.IO) {
//                                    mqttViewModel.saveSubscribedTopics(topics!!)
//                                }
//                            }
                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken?,
                            exception: Throwable?
                        ) {
                            Log.e(TAG, "subscribe action -> onFailure: asyncActionToken = $asyncActionToken, exception = ${exception?.message}")
                        }

                    }, object: IMqttMessageListener {
                        override fun messageArrived(topic: String?, message: MqttMessage?) {
                            Log.w(TAG, "subscribe messageArrived: topic = $topic, message = $message")
                        }
                    })
                // }
            }
        }
    }

    private fun onClickUnsubscribe() {
        val topic = binding.etSubscribeTopic.text.toString()
        if (topic.isNotEmpty() && !topics.isNullOrEmpty() && mqttClient != null) {
            // if (topics!!.contains(topic)) {
                mqttClient!!.unsubscribe(topic, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.w(TAG, "unsubscribe action -> onSuccess: asyncActionToken = $asyncActionToken")
//                        topics!!.remove(topic)
//                        MainScope().launch {
//                            (binding.rvTopics.adapter as MqttTopicRecyclerViewAdaptor).setTopics(
//                                topics
//                            )
//                            launch(Dispatchers.IO) {
//                                mqttViewModel.saveSubscribedTopics(topics!!)
//                            }
//                        }
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.e(TAG, "unsubscribe action -> onFailure: asyncActionToken = $asyncActionToken, exception = ${exception?.message}")
                    }
                })
            // }
        }
    }

    private fun updateStatus(status: String) {
        MainScope().launch {
            binding.tvContentMqttConnectStatus.text = status
        }
    }

    private fun showToast(message: String) {
        MainScope().launch {
            Toast.makeText(this@MqttTestActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}