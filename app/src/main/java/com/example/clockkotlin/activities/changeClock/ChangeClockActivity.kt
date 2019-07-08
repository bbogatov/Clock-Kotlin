package com.example.clockkotlin.activities.changeClock

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TimePicker
import android.widget.Toast
import com.example.clockkotlin.R
import com.example.clockkotlin.logger.Logger


/**
 * Class that used to change clock activity or remove clock.
 */
class ChangeClockActivity : AppCompatActivity(), ChangeClockContract.View {

    private lateinit var discardButton: ImageButton
    private lateinit var confirmButton: ImageButton
    private lateinit var timePicker: TimePicker
    private lateinit var deleteButton: Button
    private lateinit var defaultTime: String

    /**
     * Element in database that need remove or change
     */
    private var id: Long = 0

    /**
     * Boolean checks does user change time on [timePicker]
     */
    private var timeChanged = false

    /**
     * Presenter that contain all business logic
     */
    private lateinit var presenter: ChangeClockContract.Presenter

    /**
     * Activity that shows screen
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_clock_activity)

        presenter = ChangeClockPresenter(this)


        val intent = intent
        defaultTime = intent.getStringExtra("time")

        //Element that need to change or deleteAlarm, id means - its id in data base, not in arraylist
        id = intent.getLongExtra("id", 0)


        //Close image button
        discardButton = findViewById(R.id.close_clock_settings_image_button)
        discardButton.setOnClickListener { presenter.discardButtonClicked(timeChanged) }


        //Confirm image button
        confirmButton = findViewById(R.id.active_clock_settings_image_button)
        confirmButton.setOnClickListener {
            presenter.applyButtonClicked(
                timeChanged,
                presenter.getNewTime( timePicker),
                defaultTime,
                id
            )
        }


        //Delete button, when user clicks it current clock deletes from database
        deleteButton = findViewById(R.id.delete_button)
        deleteButton.setOnClickListener { presenter.deleteButtonClicked() }


        //Time picker that user if user wants to change time for future clock
        timePicker = findViewById(R.id.change_clock_time_picker)
        timePicker.setIs24HourView(true)
        //Method sets alarm time to time picker
        presenter.setAlarmTimeTimePicker(defaultTime, timePicker)
        timePicker.setOnTimeChangedListener { _, _, _ -> timeChanged = presenter.userChangesTime() }

    }

    /**
     * Method destroys current activity
     */
    override fun closeActivity() {
        finish()
    }

    /**
     * Method shows alert window that asks user does it really wants delete a clock.
     */
    override fun showDeleteAlertWindow() {
        val alertWindowBuilder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
        alertWindowBuilder.setCancelable(false)
        alertWindowBuilder.setMessage(R.string.delete_clock)

        alertWindowBuilder.setPositiveButton(R.string.yes_chose) { _, _ ->
            presenter.deleteClockPositiveButtonClicked(id, defaultTime)
        }

        alertWindowBuilder.setNegativeButton(R.string.no_chose) { _, _ ->
            presenter.deleteClockNegativeButtonClicked()
        }

        alertWindowBuilder.show()
        Logger.log("Shows alert window, asks to delete clock")
    }

    /**
     * When user made changes and click close button this code will run, and ask it does it wants make any changes.
     */
    override fun showAlertWindowSaveData() {
        val alertWindowBuilder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
        alertWindowBuilder.setCancelable(false)
        alertWindowBuilder.setMessage(R.string.change_data)

        alertWindowBuilder.setPositiveButton(R.string.yes_chose) { _, _ ->
            presenter.positiveAlertButtonClicked(timeChanged, presenter.getNewTime( timePicker), defaultTime, id)
        }

        alertWindowBuilder.setNegativeButton(R.string.no_chose) { _, _ ->
            presenter.negativeAlertButtonClicked()
        }

        alertWindowBuilder.show()
        Logger.log("Shows alert window, asks user to save chages")
    }

    /**
     * Shows [Toast] messange on the screen
     */
    override fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
































