package com.chuanyunglin.android.kotlin.coroutine

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.chuanyunglin.android.kotlin.BaseAppCompatActivity
import com.chuanyunglin.android.kotlin.Utils
import com.chuanyunglin.android.kotlin.databinding.ActivityCoroutingTestBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

class CoroutineTestActivity : BaseAppCompatActivity() {
    companion object {
        private val TAG = CoroutineTestActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityCoroutingTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoroutingTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e(TAG, "onCreate, start.........${Utils.getThreadName()}")
        getAllData()
//        runBlocking {
//            fun1()
//        }
//        lifecycleScope.launch(Dispatchers.Main) {
//            fun1()
//        }
        Log.e(TAG, "onCreate, end...........${Utils.getThreadName()}")
    }

    fun getAllData() {
        MainScope().launch {
            val deffereds = listOf(async {asyncFun1()}, async { asyncFun2() })
            deffereds.awaitAll()
            binding.tv.text = "Ok done...."
        }
    }

    private suspend fun asyncFun2() = withContext(Dispatchers.IO){
        delay(5000)
    }

    private suspend fun asyncFun1() = withContext(Dispatchers.Default) {
        delay(3000)
    }

    suspend fun delay(s: String, time: Long) {
        Log.e(TAG, "delay ${time/DateUtils.SECOND_IN_MILLIS}.....${Utils.getThreadName(null)}")
        kotlinx.coroutines.delay(time)
        Log.e(TAG, "end delay..........${Utils.getThreadName(null)}")
    }


}

fun main() = runBlocking {
    fun1()
}

suspend fun fun1() {
    GlobalScope.launch() {
        val time = measureTimeMillis {
            flow {
                for (i in 1..3) {
                    delay(100)//假设我们正在异步等待100毫秒
                    emit(i)//发出下一个值
                }
            }.collectLatest { value ->//取消并重新启动最新的值
                println("收集的值：$value")
                delay(300)//假设我们处理了300毫秒
                println("完成：$value")
            }
        }

        println("收集耗时：$time ms")

    }



    delay(10000)
}