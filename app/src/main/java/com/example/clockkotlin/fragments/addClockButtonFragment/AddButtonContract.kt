package com.example.clockkotlin.fragments.addClockButtonFragment

interface AddButtonContract {

    interface View {
        fun showNewClockActivity()
    }

    interface Presenter {
        fun addClockButtonPressed()
    }

}