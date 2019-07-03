package com.example.clockkotlin

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.clockkotlin.logger.Logger
import com.example.clockkotlin.receivers.AlarmReceiver
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * This objects adds, removes and changes alarms signals
 */
object Alarms {

    /**
     * Method adds new alarm
     *
     * @param time time for clock must has a lock like this 13:05 or 02:09
     * @param index index of element in database
     */
    fun addAlarm(time: String, index: Long) {
        val context = ClockApplication.applicationContext()

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = "AlarmSignal $time"
        intent.putExtra("time", time)
        intent.putExtra("index", index)


        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, getClockTime(time), pendingIntent)
        Logger.log("Created new alarm signal with time $time and index = $index")
    }

    /**
     * Method that removes clock alarm
     *  @param time time of clock that need remove must has a lock like this 13:05 or 02:09
     *  @param index index of element in database
     */
    fun removeAlarm(time: String, index: Long) {
        val context = ClockApplication.applicationContext()

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = "AlarmSignal $time"
        intent.putExtra("time", time)
        intent.putExtra("index", index)

        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Logger.log("Removed old Alarm Signal with time $time and index = $index")
    }

    /**
     * Method changes alarm time for inputted index element
     *
     * @param oldTime old time that need to change
     * @param newTime changed time
     * @param index index of element in data base that need to change
     */
    fun changeAlarmTime(oldTime: String, newTime: String, index: Long) {
        removeAlarm(oldTime, index)
        addAlarm(newTime, index)
    }

    /**
     * Makes clock active again
     *
     *  @param time time of clock that need back must has a lock like this 13:05 or 02:09
     *  @param index index of element in database
     */
    fun backAlarm(time: String, index: Long) {
        val context = ClockApplication.applicationContext()

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = "AlarmSignal $time"
        intent.putExtra("time", time)
        intent.putExtra("index", index)


        //Это отложенный интент его будет запускать аларм менеджер
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, getClockTime(time), pendingIntent)
        Logger.log("Back clock with time $time and index = $index")
    }

    /**
     * Get alarm time in milliseconds. Checks does time has past today or not.
     *
     * @param time time must has a lock for example 01:05 13:54
     */
    private fun getClockTime(time: String): Long {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, time.take(2).toInt())
        calendar.set(Calendar.MINUTE, time.takeLast(2).toInt())
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val clockTime: Date = calendar.time

        val calendarNow = Calendar.getInstance()
        val currentTime = calendarNow.time

        if (clockTime.time > currentTime.time) {
            return clockTime.time
        } else {
            return clockTime.time + TimeUnit.HOURS.toMillis(24)
        }
    }
}