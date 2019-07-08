package com.example.clockkotlin.fragments.clockListFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clockkotlin.R
import com.example.clockkotlin.adapter.ClockAdapter
import com.example.clockkotlin.databaseClockAlarm.AlarmSignal
import com.example.clockkotlin.logger.Logger


/**
 * Class that draws list of clocks.
 */
class ClockListFragment : Fragment(), ClockListContract.View {

    /**
     * Presenter that contains all logic
     */
    private lateinit var presenter: ClockListContract.Presenter

    /**
     * View that contains list of clocks
     */
    private lateinit var mView: View;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.fragment_clock_list, null)

        presenter = ClockListPresenter(this)

        presenter.addRecyclerView()

        return mView
    }

    /**
     * Method draws recycler view, list of all clocks
     */
    override fun addRecyclerView(clocksArray: ArrayList<AlarmSignal>) {
        Logger.log("Размер массива ${clocksArray.size}")
        val adapter = ClockAdapter(requireActivity(), clocksArray)

        val recyclerView: RecyclerView? = mView.findViewById(R.id.alarms_list)
        recyclerView?.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView?.adapter = adapter
    }

}