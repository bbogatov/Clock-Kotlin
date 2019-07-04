package com.example.clockkotlin.fragments.clockListFragment

import com.example.clockkotlin.database.LocalDataBase
import com.example.clockkotlin.databaseClockAlarm.AlarmSignal

class ClockListPresenter(clockListFragment: ClockListFragment) : ClockListContract.Presenter {

    override fun getClocks(): ArrayList<AlarmSignal> {
        return LocalDataBase.getClocksArray()
    }

}