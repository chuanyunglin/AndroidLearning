package com.chuanyunglin.android.kotlin

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chuanyunglin.android.kotlin.coroutine.CoroutineTestActivity
import com.chuanyunglin.android.kotlin.databinding.ActivityRecyclerViewBinding

open class RecyclerViewActivity : BaseAppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.recyclerView)
        init()
    }

    fun init() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = MainRecyclerViewAdapter(generateAllActivities())
            addItemDecoration(DividerItemDecoration(this@RecyclerViewActivity, DividerItemDecoration.HORIZONTAL))
        }
    }

    open fun generateAllActivities(): List<Pair<String, Intent>> {
        return arrayListOf(
            generatePair("LiveData Test", CoroutineTestActivity::class.java)
        )
    }

    protected fun generatePair(title: String, cls: Class<*>) : Pair<String, Intent> {
        val intent = Intent(getApplication(), cls)
        intent.putExtra("title", title)
        return Pair(title, intent)
    }
}