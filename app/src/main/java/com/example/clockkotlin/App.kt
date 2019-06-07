package com.example.clockkotlin

import android.app.Application
import android.content.Context

/**
 * This class used for creating "static" context in classes [LocalDataBase], [Alarms], [Player]
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}
