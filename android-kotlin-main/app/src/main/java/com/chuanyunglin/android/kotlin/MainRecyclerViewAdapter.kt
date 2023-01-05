package com.chuanyunglin.android.kotlin

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chuanyunglin.android.kotlin.databinding.CommonListItemBinding

open class MainRecyclerViewAdapter(var list: List<Pair<String, Intent>>?) : RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder>() {

    class MainRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        companion object {
            private val TAG = MainRecyclerViewHolder::class.java.simpleName
        }

        private val binding = CommonListItemBinding.bind(view)

        fun bind(pair: Pair<String, Intent>) {
            binding.tvTitle.text = pair.first
            binding.root.tag = pair.second;
            binding.root.setOnClickListener(this);
        }

        override fun onClick(v: View?) {
            v?.run {
                if (tag is Intent) {
                    kotlin.runCatching {
                        v.context.startActivity(tag as Intent)
                    }.onFailure {
                        Log.e(TAG, "Intent = $tag, startActivity exception =$it")
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_list_item, parent, false)
        return MainRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}