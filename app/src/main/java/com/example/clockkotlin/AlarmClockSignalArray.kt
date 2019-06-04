package com.example.clockkotlin

import android.app.AlarmManager
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * This object holds all alarm clock signals.
 */
object AlarmClockSignalArray {
    private var alarmArray: ArrayList<AlarmClockSignal> = ArrayList()

    fun addNewAlarm(alarmClockSignal: AlarmClockSignal) {
        alarmArray.add(alarmClockSignal)
    }

    fun removeAlarmSignal(id: Long) {
        for (signal in alarmArray) {
            if (signal.id == id) {
                //   signal.pendingIntent.cancel()
                signal.alarmManager.cancel(signal.pendingIntent)
                Logger.log("Signal with id = $id is OFF")
                break
            }
        }
    }

    fun backAlarmSignal(id: Long) {
        for (signal in alarmArray) {
            if (signal.id == id) {
                signal.alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    getClockTime(signal.time),
                    signal.pendingIntent
                )
                Logger.log("Signal with id = $id is ON")
                break
            }
        }
    }

    /**
     * Function deletes alarm signal from array list
     */
    fun deleteSignal(id: Long) {
        for (index in alarmArray.indices) {
            if (alarmArray[index].id == id) {
                alarmArray[index].pendingIntent.cancel()
                alarmArray[index].alarmManager.cancel(alarmArray[index].pendingIntent)
                alarmArray.removeAt(index)
                Logger.log("Signal with id = $id is DELETED")
                break
            }
        }
    }

    /**
     * Get alarm time in milliseconds. Checks does time has past today or not.
     */
    private fun getClockTime(time: String): Long {
        println(time)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, time.take(2).toInt())
        calendar.set(Calendar.MINUTE, time.takeLast(2).toInt())
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val clockTime: Date = calendar.time
        println(clockTime)

        val calendarNow = Calendar.getInstance()
        val currentTime = calendarNow.time
        if (clockTime.time > currentTime.time) {
            return clockTime.time
        } else {
            return clockTime.time + TimeUnit.HOURS.toMillis(24)
        }
    }
}