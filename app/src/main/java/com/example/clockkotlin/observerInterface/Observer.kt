package com.example.clockkotlin.observerInterface


import com.example.clockkotlin.databaseClockAlarm.AlarmSignal

import java.util.ArrayList

interface Observer {
    fun handleEvent(clockAlarms: ArrayList<AlarmSignal>)
}
