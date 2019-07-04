package com.example.clockkotlin.activities.newClock

interface NewClockContract {

    interface View {
        fun finishActivity()
        fun showToastMessage(time: String)
    }

    interface Presenter {
        fun applyButtonPressed(time: String)
        fun closeButtonPressed()
    }
}