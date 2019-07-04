package com.example.clockkotlin.activities.newClock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.clockkotlin.alarmManager.Alarms
import com.example.clockkotlin.R


/**
 * This class add new clock alarm.
 */
class NewClockActivity : AppCompatActivity(), NewClockContract.View {

    /**
     * If user click add button or discard button this method finish current activity
     */
    override fun finishActivity() {
        finish()
    }


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

    /**
     * Presenter that contains all business logic
     */
    private lateinit var presenter: NewClockContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clock_activity)

        presenter = NewClockPresenter(this)

        closeClockImageButton = findViewById(R.id.close_clock)
        activeClockImageButton = findViewById(R.id.active_clock)

        timePicker = findViewById(R.id.add_clock_time_picker)
        timePicker.setIs24HourView(true)


        closeClockImageButton.setOnClickListener { presenter.closeButtonPressed() }

        activeClockImageButton.setOnClickListener {
            presenter.applyButtonPressed(
                getString(
                    R.string.time_format_string, timePicker.currentHour,
                    timePicker.currentMinute
                )
            )
        }
    }

    /**
     * Method that shows [Toast] notification on the screen, that new clock added successfully
     */
    override fun showToastMessage(time: String) {
        Toast.makeText(
            this,
            "Added new clock on time $time",
            Toast.LENGTH_SHORT
        ).show()
    }


}