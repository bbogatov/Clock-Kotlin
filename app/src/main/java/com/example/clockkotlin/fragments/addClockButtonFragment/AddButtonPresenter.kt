package com.example.clockkotlin.fragments.addClockButtonFragment

import com.example.clockkotlin.logger.Logger

/**
 * Class that contains all logic for [AddClockButtonFragment] view.
 */
class AddButtonPresenter(private val addButtonView: AddButtonContract.View): AddButtonContract.Presenter {

    /**
     * When user clicks button add new clock, this method starts new activity in [AddClockButtonFragment]
     */
    override fun addClockButtonPressed() {
        Logger.log("Add clock button pressed")

        addButtonView.showNewClockActivity()
    }

}