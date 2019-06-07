package com.example.clockkotlin

import android.widget.Switch
import android.widget.TextView

/**
 * This class used for creating clock alarms that stores: alarm [time], [switch] can enable or disable, and it position in data base as [id]
 *
 * @param time alarm time must have a look like this 00:05, 14:39 etc.
 * @param switch switch that represents for enable or disable clock signal
 * @param id position in [LocalDataBase]
 */
class ClockAlarm(val time: TextView, var switch: Switch, val id: Long)
