package com.example.clockkotlin.fragments.clockListFragment


import com.example.clockkotlin.databaseClockAlarm.AlarmSignal

interface ClockListContract {

    interface View {
        fun addRecyclerView(clocksArray: ArrayList<AlarmSignal>)
    }

    interface Presenter {
        fun addRecyclerView()
    }
}