package com.example.clockkotlin.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.clockkotlin.ClockApplication
import com.example.clockkotlin.logger.Logger
import com.example.clockkotlin.receivers.AlarmReceiver
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * This objects adds, removes and changes alarms signals
 */
class ClockAlarmManager {

    private val extraTime = "time"
    private val extraId = "id"
    private val intentAction = "AlarmSignal"
    private val context: Context = ClockApplication.applicationContext()

    /**
     * Method adds new alarm
     *
     * @param time time for clock must has a lock like this 13:05 or 02:09
     * @param id id of element in database
     */
    fun addAlarm(time: String, id: Long) {
        val intent = getIntent(time, id)

        val pendingIntent = getPendingIntent(intent)

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            getClockTime(time), pendingIntent
        )
        Logger.log("Created new alarm signal with time $time and id = $id")
    }

    /**
     * Method that removes clock alarm
     *  @param time time of clock that need remove must has a lock like this 13:05 or 02:09
     *  @param id id of element in database
     */
    fun removeAlarm(time: String, id: Long) {

        val intent = getIntent(time, id)

        val pendingIntent = getPendingIntent(intent)

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Logger.log("Removed old Alarm Signal with time $time and id = $id")
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
     * When switch changes its value code removes or add new alarm signal.
     *
     * @param isChecked switch value, if true it activates clock, if false it inactivates clock
     * @param id        id of element that need change
     * @param time      alarm time
     */
    fun onSwitchChanged(isChecked: Boolean, id: Long, time: String) {
        if (isChecked) {
            addAlarm(time, id)
        } else {
            removeAlarm(time, id)
        }
        Logger.log(
            "Change alarm manager switch. Time = " + time + "; id = " + id +
                    "; switch old value " + !isChecked + "; switch new value " + isChecked
        )
    }

    /**
     * Method creates intent that used for pending intent and alarm manager.
     *
     * @param time time when alarm manager must start. Must have a look like this HH:MM
     *             for example 01:05 or 23:45
     * @param id   id of clock in database
     * @return returns intent that
     */
    private fun getIntent(time: String, id: Long): Intent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = "$intentAction $time"
        intent.putExtra(extraTime, time)
        intent.putExtra(extraId, id)
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        return intent
    }

    /**
     * Method returns pending intent for clock.
     *
     * @param intent alarm intent that will be pending
     * @return pending intent
     */
    private fun getPendingIntent(intent: Intent): PendingIntent {
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
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