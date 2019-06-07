package com.example.clockkotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AlertDialog


/**
 * Object that response for notification
 */
object AlarmNotification {
    private lateinit var notificationManager: NotificationManager
    private var index: Long = 0
    private lateinit var time: String

    /**
     * Function creates notification on a screen
     *
     * @param index index of alarm signal in database. Used to change switch value to false
     * @param time time of notification in database. Used to change switch value to false
     */
    fun createNotification(index: Long, time: String) {

        this.time = time
        this.index = index
        //This button need to stop playing music
        val notificationButtonIntent: Intent = Intent(App.appContext, NotificationButtonReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(App.appContext, 0, notificationButtonIntent, 0)


        val alarmNotificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(App.appContext)
        alarmNotificationBuilder.setSmallIcon(R.drawable.clock_img)
        alarmNotificationBuilder.setContentTitle("Alarm $time")
        alarmNotificationBuilder.setContentText("Wake up")
        alarmNotificationBuilder.setOngoing(true)
        alarmNotificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        alarmNotificationBuilder.addAction(R.drawable.clock_img, "I woke up", pendingIntent)


        notificationManager =
            App.appContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        //Create notification chanel for android 8.0 and high
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel =
                NotificationChannel("10001", "Clock Alarm Chanel", NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "This channel response for adding notification and playing music"
                }

            alarmNotificationBuilder.setChannelId("10001")
            notificationManager.createNotificationChannel(notificationChannel)
        }


        notificationManager.notify(1, alarmNotificationBuilder.build())
    }


    /**
     * If user clicked button "I woke up"
     */
    fun closeNotification() {
        notificationManager.cancel(1)

        val contentValues = ContentValues()
        contentValues.put("time", time)
        contentValues.put("switch", false)
        LocalDataBase.changeAlarmSwitch(index, contentValues)
    }
}