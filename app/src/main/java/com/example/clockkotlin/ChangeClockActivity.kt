package com.example.clockkotlin

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AlertDialog

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TimePicker


//В этом классе изменяется будильник время или удаление из базы данных
class ChangeClockActivity : AppCompatActivity() {

    private lateinit var closeButton: ImageButton
    private lateinit var confirmButton: ImageButton
    private lateinit var timePicker: TimePicker
    private lateinit var deleteButton: Button

    /**
     * Element in database that need remove or change
     */
    private var indexPosition: Int = 0

    /**
     * Boolean checks does user change time on [timePicker]
     */
    private var timeChanged = false

    /**
     * Activity that shows screen
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_clock_activity)

        val intent = intent
        val time = intent.getStringExtra("time")

        //Element that need to change or delete, index means - its index in data base, not in arraylist
        indexPosition = intent.getIntExtra("index", 0)


        //Close image button
        closeButton = findViewById(R.id.close_clock_settings_image_button)
        closeButton.setOnClickListener { closeButtonAction() }


        //Confirm image button
        confirmButton = findViewById(R.id.active_clock_settings_image_button)
        confirmButton.setOnClickListener { changeTimeInDataBase() }


        //Delete button, when user clicks it current clock deletes from database
        deleteButton = findViewById(R.id.delete_button)
        deleteButton.setOnClickListener { deleteClock() }


        //Time picker that user if user wants to change time for future clock
        timePicker = findViewById(R.id.change_clock_time_picker)
        timePicker.setIs24HourView(true)
        setTime(timePicker, time)

    }

    /**
     * Method sets time to timePicker. This time which user wants to change.
     */
    private fun setTime(timePicker: TimePicker, time: String) {
        val hours = time.take(2).toInt()
        val minutes = time.takeLast(2).toInt()
        timePicker.currentHour = hours
        timePicker.currentMinute = minutes
        timePicker.setOnTimeChangedListener { _, _, _ -> timeChanged = true }
    }

    /**
     * Method that checks close button, if user changes clocks time and pressed closed button
     * method ask "does he want to make a changes" if user pressed "no" returns main activity.
     * If user pressed yes changes makes to DB and returns main activity.
     */
    private fun closeButtonAction() {
        if (timeChanged) {
            alertWindow()
        } else {
            backMainActivity()
        }
    }

    /**
     * If user click close button and there was any changes in time
     * this code will show popup window and asks user does hi wants to make a changes.
     * If user wants to make a changes runs method [changeTimeInDataBase].
     * If user dont wanna make any changes returns main activity, runs [backMainActivity]
     */
    private fun alertWindow() {
        val title: String = this.getString(R.string.change_data)
        val yes: String = this.getString(R.string.yes_chose)
        val no: String = this.getString(R.string.no_chose)

        val ad: AlertDialog.Builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)

        ad.setMessage(title)
        ad.setCancelable(false)

        //Тут нужно вносить изменения в базу данных
        ad.setPositiveButton(yes, ({ _, _ -> changeTimeInDataBase() }))

        //If there is no need to change, main activity backs
        ad.setNegativeButton(no, ({ _, _ -> backMainActivity() }))


        ad.show()

    }

    /**
     * Returns main activity.
     */
    private fun backMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /**
     * If user wants to change time for current clock. This function changes data in database
     */
    private fun changeTimeInDataBase() {

        val dbHelper = DataBaseOpenHelper(this, "clock_table_data_base", null, 1)
        val clockDataBase: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(
            "time",
            getString(R.string.time_format_string, timePicker.currentHour, timePicker.currentMinute)
        )
        contentValues.put("switch", 1)

        clockDataBase.update(
            "clock_table_data_base",
            contentValues, "id = ?", arrayOf(indexPosition.toString())
        )

        println(arrayOf(indexPosition.toString()))


        Log.d(
            R.string.data_base_log.toString(),
            "Update row $indexPosition time changed to ${getString(
                R.string.time_format_string,
                timePicker.currentHour,
                timePicker.currentMinute
            )}"
        )

        dbHelper.close()
        backMainActivity()
    }

    /**
     * Method that removes clock from DB
     */
    private fun deleteClock() {
        val dbHelper = DataBaseOpenHelper(this, "clock_table_data_base", null, 1)
        val clockDataBase: SQLiteDatabase = dbHelper.writableDatabase

        clockDataBase.delete("clock_table_data_base", "id = $indexPosition", null)

        dbHelper.close()
        backMainActivity()
    }
}































