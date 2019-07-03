package com.example.clockkotlin.receivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.clockkotlin.logger.Logger
import com.example.clockkotlin.notificator.AlarmNotification
import com.example.clockkotlin.notificator.AlarmPlayer


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

        //AlarmPlayer plays song to wake up user
        AlarmPlayer.playMusic()

        //Add notification to the screen
        AlarmNotification.createNotification(index, time)

    }

}
