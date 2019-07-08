package com.example.clockkotlin.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Switch
import com.example.clockkotlin.alarmManager.ClockAlarmManager
import com.example.clockkotlin.ClockApplication
import com.example.clockkotlin.R
import com.example.clockkotlin.databaseClockAlarm.AlarmSignal
import com.example.clockkotlin.logger.Logger
import com.example.clockkotlin.observerInterface.Observed
import com.example.clockkotlin.observerInterface.Observer

/**
 * This object is local database. You can add, change and delete element from it.
 * Database implements observer pattern, if you need draw information on the screen from DB you should implement
 * [Observer] interface and add that class using method [addObserver]
 */
object LocalDataBase : Observed {

    /**
     * Object that helps create database
     */
    private var dbHelper: DataBaseOpenHelper? = null

    /**
     * Database that contain objects from [AlarmSignal] class
     */
    private var sqLiteDatabase: SQLiteDatabase? = null

    /**
     * Database version
     */
    private var dbVersion = 1

    /**
     * Array list of elements that need inform when DB has any changes
     */
    private var observersArray: ArrayList<Observer>

    /**
     * Name of column that contains time when clock must start, must be in format HH:MM in 24-hours format
     * For example 02:34,  15:05
     */
    private val TIME_DB_FIELD =
        ClockApplication.applicationContext().getResources().getString(R.string.time_db_field)

    /**
     * Name of column that contains elements switch values, can be true or false
     */
    private val SWITCH_DB_FIELD =
        ClockApplication.applicationContext().getResources().getString(R.string.switch_db_field)

    /**
     * Name of column that contains elements ID
     */
    private val ID_DB_FIELD =
        ClockApplication.applicationContext().getResources().getString(R.string.id_db_field)

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
        observersArray = ArrayList()
    }

    /**
     * Method adds new alarm clock, and returns its index in database
     *
     * @return returns index that added element ranked in array
     */
    fun addAlarm(time: String): Long {

        val contentValues = ContentValues()
        contentValues.put(TIME_DB_FIELD, time)
        contentValues.put(SWITCH_DB_FIELD, true)
        val id = sqLiteDatabase!!.insert(DATA_BASE_NAME, null, contentValues)


        Logger.log("Add new clock to data base with id $id")
        notifyObservers()
        return id
    }

    /**
     * Method removes alarm from data base
     */
    fun deleteAlarm(id: Long) {
        Logger.log("Delete clock from database with id = $id")
        sqLiteDatabase!!.delete(DATA_BASE_NAME, "$ID_DB_FIELD = $id", null)
        notifyObservers()
    }

    /**
     * Change switch value in data base.
     *
     * @param id        index of element in database
     * @param isChecked new switch value
     */
    fun changeAlarmSwitch(id: Long, isChecked: Boolean) {
        val contentValues = ContentValues()
        contentValues.put(SWITCH_DB_FIELD, isChecked)

        sqLiteDatabase!!.update(
            DATA_BASE_NAME, contentValues, "$ID_DB_FIELD = ?",
            arrayOf(id.toString())
        )
        Logger.log(
            "Updated switch in data base: id = $id; switch =  $isChecked"
        )
    }

    /**
     * Changes time for alarm
     *
     * @param id position of element that need change
     * @param newTime new clock time
     */
    fun changeTime( id: Long, newTime: String) {
        val contentValues = ContentValues()
        contentValues.put(TIME_DB_FIELD, newTime)

        sqLiteDatabase!!.update(
            DATA_BASE_NAME,
            contentValues, "$ID_DB_FIELD = ?", arrayOf(id.toString())
        )

        Logger.log("Change alarm time in database for id = $id")
        notifyObservers()
    }

    /**
     * Reads database and shows all clocks on the screen
     */
    fun getClocksArray(): ArrayList<AlarmSignal> {
        val clockAlarms: ArrayList<AlarmSignal> = ArrayList()

        val cursor: Cursor = sqLiteDatabase!!.query(DATA_BASE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {

            val idColumnIndex: Int = cursor.getColumnIndex(ID_DB_FIELD)
            val timeColumnIndex: Int = cursor.getColumnIndex(TIME_DB_FIELD)
            val switchColumnIndex: Int = cursor.getColumnIndex(SWITCH_DB_FIELD)

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

    /**
     * Add new observer that will usen if data has changes
     */
    override fun addObserver(observer: Observer) {
        observersArray.add(observer)
    }

    /**
     * Removes observer from observers list
     */
    override fun removeObserver(observer: Observer) {
        observersArray.remove(observer)
    }

    /**
     * Notifies all observers that subscribed for this class
     */
    override fun notifyObservers() {
        for (observer in observersArray) {
            observer.handleEvent(getClocksArray())
        }
    }
}