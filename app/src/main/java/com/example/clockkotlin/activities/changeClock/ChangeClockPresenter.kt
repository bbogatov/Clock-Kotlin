package com.example.clockkotlin.activities.changeClock

import android.content.Context
import android.os.Build
import android.widget.TimePicker
import com.example.clockkotlin.ClockApplication
import com.example.clockkotlin.R
import com.example.clockkotlin.alarmManager.ClockAlarmManager
import com.example.clockkotlin.database.LocalDataBase

/**
 * Class that response for business logic for [ChangeClockActivity]
 */
class ChangeClockPresenter(private var mView: ChangeClockContract.View): ChangeClockContract.Presenter {

    /**
     * Method that sets current alarm time on time picker.
     *
     * @param time time when clock should starts.
     */
    override fun setAlarmTimeTimePicker(time: String, timePicker: TimePicker) {
        val hours = time.take(2).toInt()
        val minutes = time.takeLast(2).toInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = hours
            timePicker.minute = minutes
        } else {
            timePicker.currentHour = hours
            timePicker.currentMinute = minutes
        }
    }

    /**
     * When user clicks apply button this method checks does any changes have been made.
     * If user changes nothing, method will not work.
     *
     * @param timeChanged boolean that respounce
     * @param newTime     time user chose
     * @param oldTime     old value of current clock
     * @param index       index of element in database
     */
    override fun applyButtonClicked(timeChanged: Boolean, newTime: String, oldTime: String, index: Long) {
        if (timeChanged) {
            changeTime(oldTime, newTime, index)
        }
        mView.closeActivity()
    }

    /**
     * If user clicks close button this code checks does it made any changes.
     * If user made changes it code shows alarm window and asks does user wants to save data.
     * If user dont wanna save any changes code returns main activity.
     *
     * @param timeChanged if user made changes true, in other cases false.
     */
    override fun discardButtonClicked(timeChanged: Boolean) {
        if (timeChanged) {
            mView.showAlertWindowSaveData()
        } else {
            mView.closeActivity()
        }
    }

    /**
     * If user wants delete a clock this method shows alert window, and ask does he sure.
     */
    override fun deleteButtonClicked() {
        mView.showDeleteAlertWindow()
    }

    /**
     * Method deletes clock from database and removes its alarm manager.
     *
     * When user click delete clock method shows alarm window and aks does it sure.
     * If user sures it clicks positive button and this method runs.
     *
     * @param index index of element in database that need remove
     * @param time  time when alarm clock must started
     */
    override fun deleteClockPositiveButtonClicked(id: Long, time: String) {
        deleteClockInDataBase(id)
        deleteAlarmManager(id, time)
        mView.showToastMessage(ClockApplication.applicationContext().getString(R.string.delete_clock_successfully))
    }

    /**
     * If user clicks delete clock AlertWindow appears and ask him does he sure.
     * If user clicks negative button on alert window, this code runs and destroys [ChangeClockActivity]activity
     */
    override fun deleteClockNegativeButtonClicked() {
        mView.closeActivity()
    }

    /**
     * If user changes time on [TimePicker]
     *
     * @return true when user changes time on timePicker
     */
    override fun userChangesTime(): Boolean {
        return true
    }

    /**
     * User made changes and click close button, after it appears alert window that asks does he want make any changes
     * If user clicks no this code will work, and destroys [ChangeClockActivity] activity
     */
    override fun negativeAlertButtonClicked() {
        mView.closeActivity()
    }

    /**
     * When user has made changes and click discard button this
     *
     * @param timeChanged boolean that respounce
     * @param newTime     time user chose
     * @param oldTime     old value of current clock
     * @param index       index of element in database
     */
    override fun positiveAlertButtonClicked(timeChanged: Boolean, newTime: String, oldTime: String, index: Long) {
        applyButtonClicked(timeChanged, newTime, oldTime, index)
        mView.closeActivity()
    }

    /**
     * Method that changes alarm time in database and changes alarmManger.
     *
     * @param oldTime old value that need change
     * @param newTime new value that need will be
     * @param id index of element in database
     */
    private fun changeTime(oldTime: String, newTime: String, id: Long) {
        changeAlarmManger(oldTime, newTime, id)
        changeValueInDataBase(newTime, id)
    }

    /**
     * Method that changes alarm time.
     *
     * @param oldTime old time that need change
     * @param newTime future clock time
     * @param index   index of element in DB
     */
    private fun changeAlarmManger(oldTime: String, newTime: String, id: Long) {
        val alarmManager = ClockAlarmManager()
        alarmManager.changeAlarmTime(oldTime, newTime, id)
    }

    /**
     * Method that changes values in database.
     *
     * @param newTime new time value
     * @param id   index of element in DB that need change
     */
    private fun changeValueInDataBase(newTime: String, id: Long) {
        LocalDataBase.changeTime(id, newTime)
        LocalDataBase.changeAlarmSwitch(id, true)
    }

    /**
     * Method that removes clock from database.
     *
     * @param id index of element that need remove
     */
    private fun deleteClockInDataBase(id: Long) {
        LocalDataBase.deleteAlarm(id)
    }

    /**
     * Method that removes alarm manager.
     *
     * @param id index if element in database
     * @param time  time when alarm manger must started
     */
    private fun deleteAlarmManager(id: Long, time: String) {
        ClockAlarmManager().removeAlarm(time, id)
    }

    /**
     * Method that removes clock from DB
     */
    override fun getNewTime(timePicker: TimePicker): String {

        val hours = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour
        } else {
            timePicker.currentHour
        }

        val minutes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.minute
        } else {
            timePicker.currentMinute
        }


        return ClockApplication.applicationContext().getString(R.string.time_format_string, hours, minutes)
    }
}