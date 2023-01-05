package com.chuanyunglin.android.kotlin

import com.chuanyunglin.android.kotlin.test.MyUnitClass
import org.junit.Assert.assertEquals
import org.junit.Test

class MyUnitTest {

//    @Test
//    fun testAdd() {
//        println("first.....")
//
//        val act2 = MyUnitClass().add(1, 2)
//        val expect2 = 3
//        assertEquals("加法測試失敗2", expect2, act2)
//
//
//    }

    @Test
    fun testAdd2() {
        println("second.....")


        val act = MyUnitClass().add(1, 2)
        val expect = 4
        assertEquals("加法測試失敗", expect, act)
    }
}