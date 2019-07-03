package com.example.clockkotlin.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Switch
import android.widget.TextView
import com.example.clockkotlin.Alarms
import com.example.clockkotlin.ClockApplication
import com.example.clockkotlin.R
import com.example.clockkotlin.databaseClockAlarm.AlarmSignal
import com.example.clockkotlin.logger.Logger

/**
 * This object is local database. You can add, change and delete element from it.
 */
object LocalDataBase {
    private var dbHelper: DataBaseOpenHelper? = null
    private var sqLiteDatabase: SQLiteDatabase? = null
    private var dbVersion = 1

    /**
     * Database name that stores all clock alarms
     */
    private val DATA_BASE_NAME =
        ClockApplication.applicationContext().getResources().getString(R.string.alarms_data_base_name)

    init {
        dbHelper = DataBaseOpenHelper(
            ClockApplication.applicationContext(),
            DATA_BASE_NAME,
            null,
            dbVersion
        )
        sqLiteDatabase = dbHelper!!.writableDatabase
    }

    /**
     * Method adds new alarm clock, and returns its index in database
     *
     * @return returns index that added element ranked in array
     */
    fun addAlarm(cv: ContentValues): Long {
        val id = sqLiteDatabase!!.insert("clock_table_data_base", null, cv)
        Logger.log("Add new clock to data base with id $id")
        return id
    }

    /**
     * Method removes alarm from data base
     */
    fun deleteAlarm(indexPosition: Long) {
        Logger.log("Delete clock from database with id = $indexPosition")
        sqLiteDatabase!!.delete("clock_table_data_base", "id = $indexPosition", null)
    }

    /**
     * Changes enable value in database
     *
     * @param indexPosition index in database that need change
     * @param contentValues updated content value
     */
    fun changeAlarmSwitch(indexPosition: Long, contentValues: ContentValues) {
        sqLiteDatabase!!.update(
            "clock_table_data_base", contentValues, "id = ?",
            arrayOf(indexPosition.toString())
        )
        Logger.log("Change alarm enable in database for id = $indexPosition")
    }

    /**
     * Changes time for alarm
     *
     * @param contentValues updated content value
     * @param indexPosition position of element that need change
     * @param oldTime time that need to change
     * @param newTime future clock time
     */
    fun changeTime(
        contentValues: ContentValues, indexPosition: Long, oldTime: String, newTime: String
    ) {

        sqLiteDatabase!!.update(
            "clock_table_data_base",
            contentValues, "id = ?", arrayOf(indexPosition.toString())
        )
        Alarms.changeAlarmTime(oldTime, newTime, indexPosition)
        Logger.log("Change alarm time in database for id = $indexPosition")

    }

    /**
     * Reads database and shows all clocks on the screen
     */
    fun getClocksArray(): ArrayList<AlarmSignal> {
        val clockAlarms: ArrayList<AlarmSignal> = ArrayList()

        val cursor: Cursor = sqLiteDatabase!!.query(DATA_BASE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {

            val idColumnIndex: Int = cursor.getColumnIndex("id")
            val timeColumnIndex: Int = cursor.getColumnIndex("time")
            val switchColumnIndex: Int = cursor.getColumnIndex("enable")

            do {

                clockAlarms.add(
                    AlarmSignal(
                        cursor.getLong(idColumnIndex),
                        cursor.getString(timeColumnIndex),
                        cursor.getInt(switchColumnIndex) > 0
                    )
                )


            } while ((cursor.moveToNext()))
        }

        cursor.close()

        return clockAlarms
    }
}