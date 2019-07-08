package com.example.clockkotlin.fragments.clockListFragment

import com.example.clockkotlin.database.LocalDataBase

/**
 * Class contains business logic for recycler view
 */
class ClockListPresenter(private var mView: ClockListContract.View) : ClockListContract.Presenter {

    /**
     * Method tells recycler view draw clock array
     */
    override fun addRecyclerView() {
        mView.addRecyclerView(LocalDataBase.getClocksArray())
    }

}