package com.example.clockkotlin.fragments.clockListFragment

import com.example.clockkotlin.databaseClockAlarm.AlarmSignal

interface ClockListContract {

    interface View{

    }

    interface Presenter {
        fun getClocks(): ArrayList<AlarmSignal>
    }
}