package com.example.clockkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.clock_activity.view.*

private const val NEW_CLOCK_DETECTED = "com.example.clockkotlin.NEW_CLOCK"


class ClockActivity : AppCompatActivity() {

    private lateinit var closeClockImageButton: ImageButton
    private lateinit var activeClockImageButton: ImageButton
    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clock_activity)


        closeClockImageButton = findViewById(R.id.close_clock)
        activeClockImageButton = findViewById(R.id.active_clock)
        timePicker = findViewById(R.id.time_picker)
        timePicker.setIs24HourView(true)

        closeClockImageButton.setOnClickListener { backMainActivity() }
        activeClockImageButton.setOnClickListener { addAlarm() }
    }

    private fun backMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun addAlarm() {

        //Current hours, if hours equals in range 00-09 int number will be 0-9 that doesn't look fine
        //now it will be looking like this 02:04

        val textView = TextView(this)
        textView.text = getString(R.string.time_format_string, timePicker.currentHour, timePicker.currentMinute)


        val newClock = Intent(NEW_CLOCK_DETECTED)
        newClock.putExtra(
            "time",
            getString(R.string.time_format_string, timePicker.currentHour, timePicker.currentMinute)
        )
        sendBroadcast(newClock)


         backMainActivity()
    }
}