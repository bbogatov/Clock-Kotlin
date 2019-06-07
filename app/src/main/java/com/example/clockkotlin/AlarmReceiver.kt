package com.example.clockkotlin


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.sql.SQLOutput


/**
 * This class adds notification and plays sound.
 */
class AlarmReceiver : BroadcastReceiver() {

    /**
     * Method play sound and adds notification
     */
    override fun onReceive(context: Context, intent: Intent) {
        Logger.log("Get notification to start clock")

        val time = intent.getStringExtra("time")
        val index = intent.getLongExtra("index", 0)

        //Player plays song to wake up user
        Player.playMusic()

        //Add notification to the screen
        AlarmNotification.createNotification(index, time)

    }

}
