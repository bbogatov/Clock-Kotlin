package com.example.clockkotlin.adapter

import com.example.clockkotlin.alarmManager.ClockAlarmManager
import com.example.clockkotlin.database.LocalDataBase

class ClockListPresenter(private val alarmHolderView: ClockAdapter.AlarmHolderView) : ClockAdapterContract.Presenter {

    /**
     * Code changes switch value in database and changes AlarmManger signal
     *
     * @param id   index of element that need change
     * @param aSwitch new switch value
     * @param time time when clock must start working
     */
    override fun switchPressed(id: Long, aSwitch: Boolean, time: String) {
        changeDataBase(id, aSwitch)
        changeAlarmManger(id, aSwitch, time)
    }

    /**
     * If user pressed text view, it starts next activity where user can change time or delete clock
     */
    override fun textViewPressed() {
        alarmHolderView.startChangeClockActivity()
    }

    /**
     * Method changes switch value in database
     *
     * @param id   index of element that need change
     * @param aSwitch new switch value
     */
    private fun changeDataBase(id: Long, aSwitch: Boolean) {
        LocalDataBase.changeAlarmSwitch(id, aSwitch)
    }

    /**
     * Method that changes [ClockAlarmManager] removes or makes it active.
     *
     * @param index   index of element that need change
     * @param aSwitch future switch value
     * @param time time for clock that need change
     */
    private fun changeAlarmManger(id: Long, aSwitch: Boolean, time: String) {
        ClockAlarmManager().onSwitchChanged(aSwitch, id, time)
    }

}
