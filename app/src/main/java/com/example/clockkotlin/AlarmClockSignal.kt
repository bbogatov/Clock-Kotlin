package com.example.clockkotlin

import android.app.AlarmManager
import android.app.PendingIntent

class AlarmClockSignal(var pendingIntent: PendingIntent, var alarmManager: AlarmManager, var id: Long, var time: String)