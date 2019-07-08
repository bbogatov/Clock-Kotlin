package com.example.clockkotlin.notificator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.example.clockkotlin.ClockApplication
import com.example.clockkotlin.R
import com.example.clockkotlin.database.LocalDataBase
import com.example.clockkotlin.receivers.NotificationButtonReceiver


/**
 * Object that response for notification
 */
object AlarmNotification {

    private var notificationManager: NotificationManager
    private lateinit var time: String


    init {
        notificationManager =
            ClockApplication.applicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
     * Function creates notification on a screen
     *
     * @param index index of alarm signal in database. Used to change enable value to false
     * @param time time of notification in database. Used to change enable value to false
     */
    fun createNotification( time: String) {

        this.time = time
        //This button need to stop playing music
        val notificationButtonIntent: Intent = Intent(ClockApplication.applicationContext(), NotificationButtonReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(ClockApplication.applicationContext(), 0, notificationButtonIntent, 0)


        val alarmNotificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(ClockApplication.applicationContext());
        alarmNotificationBuilder.setSmallIcon(R.drawable.clock_img)
        alarmNotificationBuilder.setContentTitle("Alarm $time")
        alarmNotificationBuilder.setContentText("Wake up")
        alarmNotificationBuilder.setOngoing(true)
        alarmNotificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        alarmNotificationBuilder.addAction(R.drawable.clock_img, "I woke up", pendingIntent)


        notificationManager =
            ClockApplication.applicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        //Create notification chanel for android 8.0 and high
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel =
                NotificationChannel("10001", "Clock Alarm Chanel", NotificationManager.IMPORTANCE_HIGH)

            alarmNotificationBuilder.setChannelId("10001")
            notificationManager.createNotificationChannel(notificationChannel)
        }


        notificationManager.notify(1, alarmNotificationBuilder.build())
    }

    /**
     * If user clicked button "I woke up" on notification it  removes it.
     */
    fun closeNotification() {
        notificationManager.cancel(1)
    }
}