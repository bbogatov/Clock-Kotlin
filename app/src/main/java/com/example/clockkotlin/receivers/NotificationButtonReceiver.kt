package com.example.clockkotlin.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.clockkotlin.logger.Logger
import com.example.clockkotlin.notificator.AlarmNotification
import com.example.clockkotlin.notificator.AlarmPlayer


/**
 * This class removes notification when user wakes up
 */
class NotificationButtonReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Logger.log("Received instruction to stop player")
        AlarmPlayer.stopPlayer()
        //Remove notification from screen
        AlarmNotification.closeNotification()
    }
}
