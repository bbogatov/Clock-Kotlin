package com.example.clockkotlin

import android.app.Application
import android.content.Context

class ClockApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ClockApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = ClockApplication.applicationContext()
    }
}