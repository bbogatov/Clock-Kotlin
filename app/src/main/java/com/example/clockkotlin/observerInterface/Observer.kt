package com.example.clockkotlin.observerInterface


import com.example.clockkotlin.databaseClockAlarm.AlarmSignal

import java.util.ArrayList

/**
 * Interface that used for "Observer" pattern.
 * If your class need know when database has any changes, you should extend this interface and add this class to database
 * using class [Observed.addObserver] from [Observed] class.
 * If you will don't need it anymore you can delete it using [Observed.removeObserver]
. */
interface Observer {
    fun handleEvent(clockAlarms: ArrayList<AlarmSignal>)
}
