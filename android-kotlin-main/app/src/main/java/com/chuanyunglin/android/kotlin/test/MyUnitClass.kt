package com.chuanyunglin.android.kotlin.test

import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class MyUnitClass {
    fun add(a: Int, b: Int) = (a+b)
}

fun main() {
    MyJava.test()
}