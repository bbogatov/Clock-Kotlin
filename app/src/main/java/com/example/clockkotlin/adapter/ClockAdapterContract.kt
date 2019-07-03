package com.example.clockkotlin.adapter

interface ClockAdapterContract {
    interface View {
        fun changeAlarmTime()
    }

    interface Presenter {
        fun switchPressed(index: Long, aSwitch: Boolean, time: String)
        fun textViewPressed()
    }
}