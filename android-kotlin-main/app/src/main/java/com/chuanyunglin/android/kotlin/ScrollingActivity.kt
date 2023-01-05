package com.chuanyunglin.android.kotlin

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chuanyunglin.android.kotlin.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "This is my kotlin learning project", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        initOthers()
    }

    private fun initOthers() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val list = mainViewModel.generateAllActivities()
        val linearLayoutManager = LinearLayoutManager(this)
        binding.contentScrolling.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = MainRecyclerViewAdapter(list)
            addItemDecoration(DividerItemDecoration(this@ScrollingActivity, DividerItemDecoration.HORIZONTAL))
        }
    }
}