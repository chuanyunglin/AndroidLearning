package com.chuanyunglin.android.kotlin.mqtt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MqttTopicRecyclerViewAdaptor(topics: Set<String>?) : RecyclerView.Adapter<MqttTopicRecyclerViewAdaptor.ViewHolder>() {
    private var topicList: ArrayList<String>? = null;

    init {
        topicList = if (topics == null) null else ArrayList<String>(topics)
        topicList?.sort()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = topicList?.get(position)
    }

    override fun getItemCount(): Int {
        return topicList?.size ?: 0
    }

    fun setTopics(topics: Set<String>?) {
        topicList = if (topics == null) null else ArrayList<String>(topics)
        topicList?.sort()
        notifyDataSetChanged();
    }
}