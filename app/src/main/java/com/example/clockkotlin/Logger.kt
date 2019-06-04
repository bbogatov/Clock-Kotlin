package com.example.clockkotlin

import android.util.Log

object Logger {
    private const val CLOCK_LOG = "clock_log"

    fun log(messange: String) {
        Log.d(CLOCK_LOG, messange)
    }
}