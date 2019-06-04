package com.example.clockkotlin

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * This class add new clock alarm.
 */
class ClockActivity : AppCompatActivity() {


    private lateinit var closeClockImageButton: ImageButton
    private lateinit var activeClockImageButton: ImageButton
    private lateinit var timePicker: TimePicker

    //Alarms DB
    private lateinit var clockDataBase: SQLiteDatabase
    private lateinit var dbHelper: DataBaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clock_activity)


        closeClockImageButton = findViewById(R.id.close_clock)
        activeClockImageButton = findViewById(R.id.active_clock)
        timePicker = findViewById(R.id.add_clock_time_picker)
        timePicker.setIs24HourView(true)

        closeClockImageButton.setOnClickListener { backMainActivity() }
        activeClockImageButton.setOnClickListener { addAlarm() }
    }

    /**
     * Method returns main activity to the screen.
     */
    private fun backMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    /**
     * Method adds alarm and backs main activity
     */
    private fun addAlarm() {

        //Current hours, if hours equals in range 00-09 int number will be 0-9 that doesn't look fine
        //now it will be looking like this 02:04
        val textView = TextView(this)
        textView.text = getString(
            R.string.time_format_string, timePicker.currentHour,
            timePicker.currentMinute
        )

        val time = getString(
            R.string.time_format_string, timePicker.currentHour,
            timePicker.currentMinute
        )

        val id = addClockToDataBase(time)
        addClock(time, id)

        backMainActivity()
    }


    /**
     * Method that adds new alarm clock.
     *
     * @param time alarm time
     */
    fun addClock(time: String, id: Long) {

        println("Апликейшен контекст создаю  = $this")


        val intent = Intent(this, AlarmReceiver::class.java)
        intent.action = "ClockAlarm $time"
        intent.putExtra("time", time)

        //Это отложенный интент его будет запускать аларм менеджер
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager: AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, getClockTime(time), pendingIntent)

        AlarmClockSignalArray.addNewAlarm(AlarmClockSignal(pendingIntent, alarmManager, id, time))

    }


    fun addClockToDataBase(time: String): Long {
        //Записываем в базу данных
        dbHelper = DataBaseOpenHelper(this, "clock_table_data_base", null, 1)
        clockDataBase = dbHelper.writableDatabase

        //New clock object
        val cv = ContentValues()

        //Alarm time, user chose it
        cv.put("time", time)

        //default switch value
        cv.put("switch", true)

        val id = clockDataBase.insert("clock_table_data_base", null, cv)
        Log.d("clock_table_data_base", "add new clock to data base with id $id")

        clockDataBase.close()
        dbHelper.close()

        Toast.makeText(
            this,
            "Будильник добавлен на $time",
            Toast.LENGTH_SHORT
        ).show()

        return id
    }

    /**
     * Get alarm time in milliseconds. Checks does time has past today or not.
     */
    private fun getClockTime(time: String): Long {

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