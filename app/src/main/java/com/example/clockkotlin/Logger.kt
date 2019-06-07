package com.example.clockkotlin

import android.util.Log

/**
 * Object that used for log operations
 */
object Logger {
    private const val CLOCK_LOG = "clock_log"

    fun log(messange: String) {
        Log.d(CLOCK_LOG, messange)
    }
}