package com.example.clockkotlin

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat


/**
 * Object that response for notification
 */
object AlarmNotification {
    private lateinit var notificationManager: NotificationManager


    fun createNotification(context: Context) {
        //This button need to stop playing music
        val notificationButtonIntent: Intent = Intent(context, NotificationButtonReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationButtonIntent, 0)


        //This adds notification
        val alarmNotificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
        alarmNotificationBuilder.setSmallIcon(R.drawable.clock_img)
        alarmNotificationBuilder.setContentTitle("Alarm")
        alarmNotificationBuilder.setContentText("Wake up")
        alarmNotificationBuilder.setOngoing(true)
        alarmNotificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        alarmNotificationBuilder.addAction(R.drawable.clock_img, "I woke up", pendingIntent)

        val notification = alarmNotificationBuilder.build()


        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    fun closeNotification() {
        notificationManager.cancel(1)
    }
}