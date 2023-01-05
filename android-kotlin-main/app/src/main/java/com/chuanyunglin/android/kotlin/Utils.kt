package com.chuanyunglin.android.kotlin

class Utils {
    companion object {
        fun getThreadName(thread: Thread? = null): String {
            return if (thread != null) {
                thread.name
            } else {
                Thread.currentThread().name
            }
        }
    }
}