package com.chuanyunglin.android.kotlin.test

import java.lang.ArithmeticException
import java.util.*

data class MyData(val name: String, val age: Int)

interface A {
    fun print(str: String) {
        println(str)
    }
}

class B(val a: A = object: A {
}) : A by a

object MySig {
    val address = "abc"
}

val mm = 10

class MM {
}

fun MM.hell(): String {
    return "mm"
}

fun excep() {
    //try {
        10/0
    //} finally {
    //    println("after 10/0")
    //}
    print("exit excep")
}

fun main() {
    // MyJava.run()

    try {
        excep()
    } catch (e: ArithmeticException) {
        println("Got error：" + e.message)
    }


    val b = listOf<String>("a", "c", "bd")
    println(b.maxBy { it })
    println(b.maxBy { it.length })
}

