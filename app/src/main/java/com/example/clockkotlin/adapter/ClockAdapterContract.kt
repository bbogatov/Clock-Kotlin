package com.example.clockkotlin.adapter

interface ClockAdapterContract {
    interface View {
        fun startChangeClockActivity()
    }

    interface Presenter {
        fun switchPressed(id: Long, aSwitch: Boolean, time: String)
        fun textViewPressed()
    }
}