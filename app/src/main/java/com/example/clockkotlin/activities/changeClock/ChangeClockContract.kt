package com.example.clockkotlin.activities.changeClock

import android.widget.TimePicker

interface ChangeClockContract {

    interface View {
        fun closeActivity()
        fun showDeleteAlertWindow()
        fun showAlertWindowSaveData()
        fun showToastMessage(message: String)
    }

    interface Presenter {
        fun getNewTime(timePicker: TimePicker): String
        fun setAlarmTimeTimePicker(time: String, timePicker: TimePicker)
        fun applyButtonClicked(timeChanged: Boolean, newTime: String, oldTime: String, index: Long)
        fun discardButtonClicked(timeChanged: Boolean)
        fun deleteButtonClicked()
        fun deleteClockPositiveButtonClicked(id: Long, time: String)
        fun deleteClockNegativeButtonClicked()
        fun userChangesTime(): Boolean
        fun negativeAlertButtonClicked()
        fun positiveAlertButtonClicked(timeChanged: Boolean, newTime: String, oldTime: String, index: Long)
    }

}