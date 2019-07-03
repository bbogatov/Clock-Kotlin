package com.example.clockkotlin.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.clockkotlin.Alarms
import com.example.clockkotlin.MainActivity
import com.example.clockkotlin.R
import com.example.clockkotlin.database.LocalDataBase


/**
 * This class add new clock alarm.
 */
class AddNewClockActivity : AppCompatActivity() {

    /**
     * If user don't wanna add new clock, user clicks this button
     */
    private lateinit var closeClockImageButton: ImageButton

    /**
     * Button that adds new clock to database
     */
    private lateinit var activeClockImageButton: ImageButton

    /**
     * User picks time
     */
    private lateinit var timePicker: TimePicker

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
     * Method returns main activity with new added clock
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
    private fun addClock(time: String, id: Long) {
        Alarms.addAlarm(time, id)
    }

    /**
     * Method that adds new clock to database, and shows [Toast] notification on the screen
     */
    private fun addClockToDataBase(time: String): Long {

        val cv = ContentValues()
        cv.put("time", time)
        cv.put("enable", true)

        val id = LocalDataBase.addAlarm(cv)


        Toast.makeText(
            this,
            "Added new clock on time $time",
            Toast.LENGTH_SHORT
        ).show()

        return id
    }

}