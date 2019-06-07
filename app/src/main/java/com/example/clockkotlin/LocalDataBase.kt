package com.example.clockkotlin

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast

/**
 * This object is local database. You can add, change and delete element from it.
 */
object LocalDataBase {
    private var dbHelper: DataBaseOpenHelper? = null
    private var clockDataBase: SQLiteDatabase? = null


    init {
        dbHelper = DataBaseOpenHelper(App.appContext!!, "clock_table_data_base", null, 1)
        clockDataBase = dbHelper!!.writableDatabase
    }

    /**
     * Method adds new alarm clock, and returns its index in database
     *
     * @return returns index that added element ranked in array
     */
    fun addAlarm(cv: ContentValues): Long {
        val id = clockDataBase!!.insert("clock_table_data_base", null, cv)
        Logger.log("Add new clock to data base with id $id")
        return id
    }

    /**
     * Method removes alarm from data base
     */
    fun deleteAlarm(indexPosition: Long) {
        Logger.log("Delete clock from database with id = $indexPosition")
        clockDataBase!!.delete("clock_table_data_base", "id = $indexPosition", null)
    }

    /**
     * Changes switch value in database
     *
     * @param indexPosition index in database that need change
     * @param contentValues updated content value
     */
    fun changeAlarmSwitch(indexPosition: Long, contentValues: ContentValues) {
        clockDataBase!!.update(
            "clock_table_data_base", contentValues, "id = ?",
            arrayOf(indexPosition.toString())
        )
        Logger.log("Change alarm switch in database for id = $indexPosition")
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

        clockDataBase!!.update(
            "clock_table_data_base",
            contentValues, "id = ?", arrayOf(indexPosition.toString())
        )
        Alarms.changeAlarmTime(oldTime, newTime, indexPosition)
        Logger.log("Change alarm time in database for id = $indexPosition")

    }

    /**
     * Close data base after application terminated
     */
    fun closeDataBase() {
        dbHelper!!.close()
    }
}