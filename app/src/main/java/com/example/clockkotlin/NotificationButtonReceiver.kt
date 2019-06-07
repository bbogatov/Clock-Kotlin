package com.example.clockkotlin

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent


/**
 * This class removes notification when user wakes up
 */
class NotificationButtonReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Logger.log("Received instruction to stop player")
        Player.stopPlayer()
        //Remove notification from screen
        AlarmNotification.closeNotification()
    }
}
