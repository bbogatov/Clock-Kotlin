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

    //button that runs activity that adds new alarm time
    lateinit var addClockImageButton: ImageButton

    private var alarms: ArrayList<ClockAlarm> = ArrayList()
    private lateinit var clockAdapter: ClockAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getClocksArray()


        addClockImageButton = findViewById(R.id.clock_button)

        addClockImageButton.setOnClickListener { createClockActivity() }


        clockAdapter = ClockAdapter(this, alarms)

        val clockAlarmListView: ListView = findViewById(R.id.clock_list_view)
        clockAlarmListView.adapter = clockAdapter

    }

    private fun createClockActivity() {
        val intent = Intent(this, ClockActivity::class.java)
        startActivity(intent)
    }

    //Тут читается база данных со всеми будильниками, и вносится в массив данных который будет отображаться на экране
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

}
