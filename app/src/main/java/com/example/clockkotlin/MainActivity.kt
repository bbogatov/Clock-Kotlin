package com.example.clockkotlin

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView

/**
 * Main activity, from this activity application starts
 */
class MainActivity : AppCompatActivity() {

    /**
     * Button that starts activity that adds new clock
     */
    lateinit var addClockImageButton: ImageButton

    /**
     * Array list that keeps all clock and shows them on screen
     */
    private var alarms: ArrayList<ClockAlarm> = ArrayList()

    /**
     * Adapter that shows all clocks using recycler view
     */
    private lateinit var clockAdapter: ClockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getClocksArray()

        addClockImageButton = findViewById(R.id.clock_button)

        addClockImageButton.setOnClickListener { createNewClockActivity() }


        clockAdapter = ClockAdapter(this, alarms)

        val clockAlarmListView: ListView = findViewById(R.id.clock_list_view)
        clockAlarmListView.adapter = clockAdapter

    }

    /**
     * Method shows screen where user can peek a time for new clock
     */
    private fun createNewClockActivity() {
        val intent = Intent(this, AddNewClockActivity::class.java)
        startActivity(intent)
    }

    /**
     * Reads database and shows all clocks on the screen
     */
    private fun getClocksArray() {
        val dbHelper = DataBaseOpenHelper(this, "clock_table_data_base", null, 1)
        val clockDataBase: SQLiteDatabase = dbHelper.writableDatabase
        val cursor: Cursor = clockDataBase.query("clock_table_data_base", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {

            val timeColumnIndex = cursor.getColumnIndex("time")
            val switchColumnIndex = cursor.getColumnIndex("switch")
            val idColumnIndex = cursor.getColumnIndex("id")

            do {
                val currentTextView = TextView(this)
                val currentSwitch = Switch(this)
                val id = cursor.getLong(idColumnIndex)


                currentTextView.text = cursor.getString(timeColumnIndex)
                currentSwitch.isChecked = cursor.getInt(switchColumnIndex) > 0

                alarms.add(ClockAlarm(currentTextView, currentSwitch, id))

            } while ((cursor.moveToNext()))
        }

        clockDataBase.close()
        dbHelper.close()

        cursor.close()
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalDataBase.closeDataBase()
    }
}
