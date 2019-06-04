package com.example.clockkotlin


import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * This class adds notification and plays sound.
 */
class AlarmReceiver : BroadcastReceiver() {

    /**
     * Method play sound and adds notification to the screen
     */
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NOTIF_TAG", "Get notification to start clock")

        //Player plays song to wake up user
        Player.playMusic(context)

        //Add notification to the screen
        AlarmNotification.createNotification(context)

    }

}
