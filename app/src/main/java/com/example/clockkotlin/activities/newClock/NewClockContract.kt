package com.example.clockkotlin.activities.newClock

interface NewClockContract {

    interface View {
        fun finishActivity()
        fun showToastMessage(message: String)
    }

    interface Presenter {
        fun applyButtonPressed(time: String)
        fun closeButtonPressed()
    }
}