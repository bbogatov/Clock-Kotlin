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

class MainActivity : AppCompatActivity() {

    lateinit var clockImageButton: ImageButton
    private lateinit var clockAdapter: ClockAdapter
    private var alarms: ArrayList<ClockAlarm> = ArrayList()
    lateinit var clockDataBase: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clockAdapter = ClockAdapter(this, alarms)

        clockImageButton = findViewById(R.id.clock_button)
        clockImageButton.setOnClickListener { createClockActivity() }


        val clockAlarmListView: ListView = findViewById(R.id.clock_list_view)
        clockAlarmListView.adapter = clockAdapter

        getClocksArray()
    }

    private fun createClockActivity() {
        val intent = Intent(this, ClockActivity::class.java)
        startActivity(intent)
    }

    private fun getClocksArray() {
        val dbHelper = DataBaseOpenHelper(this, "clock_table_data_base", null, 1)
        val clockDataBase: SQLiteDatabase = dbHelper.writableDatabase
        val cursor: Cursor = clockDataBase.query("clock_table_data_base", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {

            val timeColumnIndex = cursor.getColumnIndex("time")
            val switchColumnIndex = cursor.getColumnIndex("switch")

            do {

                val currentTextView = TextView(this)
                val currentSwitch = Switch(this)
                currentTextView.text = cursor.getString(timeColumnIndex)
                currentSwitch.isChecked = cursor.getString(switchColumnIndex).toInt() == 1
                alarms.add(ClockAlarm(currentTextView, currentSwitch))

            } while ((cursor.moveToNext()))
        }


        cursor.close()
    }

}
