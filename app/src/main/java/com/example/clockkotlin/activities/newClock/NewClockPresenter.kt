package com.example.clockkotlin.activities.newClock

import com.example.clockkotlin.alarmManager.ClockAlarmManager
import com.example.clockkotlin.database.LocalDataBase

/**
 * Class that contains logic for [NewClockActivity] class.
 * This class add new clock to database and creates new alarm manager.
 */
class NewClockPresenter(private val mView: NewClockContract.View) : NewClockContract.Presenter {

    /**
     * Method adds new clock to database, shows {@link android.widget.Toast} message and returns
     * main activity.
     *
     * @param time time in witch clock will starts
     */
    @Override
    override fun applyButtonPressed(time: String) {
        val id = addNewClock(time)
        addAlarmManger(time, id)
        mView.showToastMessage("Added new clock on time $time")
        closeActivity()
    }

    /**
     * If user pressed close button this method closed current activity
     */
    override fun closeButtonPressed() {
        closeActivity()
    }

    /**
     * Method adds new clock to database.
     *
     * @param time time when starts clock alarm, should lock like this HH:mm for example 15:05 or 02:34
     *             using 24-hours format with leading zeroes
     * @return index of new added clock from database, this index auto increasing
     */
    private fun addNewClock(time: String): Long {
        return LocalDataBase.addAlarm(time)
    }

    /**
     * Method create new alarm manager with specific time
     */
    private fun addAlarmManger(time: String, id: Long) {
        ClockAlarmManager().addAlarm(time, id)
    }

    /**
     * When user clicks apply or discard button this method runs and removes [NewClockActivity]
     */
    private fun closeActivity() {
        mView.finishActivity()
    }
}