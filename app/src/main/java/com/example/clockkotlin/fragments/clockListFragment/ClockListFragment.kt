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
import com.example.clockkotlin.database.LocalDataBase
import com.example.clockkotlin.databaseClockAlarm.AlarmSignal


class ClockListFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_clock_list, null)

        addRecyclerView(view)

        return view
    }

    private fun addRecyclerView(wView: View) {

        val clockArray = LocalDataBase.getClocksArray()
        //Not sure about activity as Activity

        //TODO нужно удалить будет это заполнение массива
        if (clockArray.size == 0) {
            for (i in 1..10) {
                clockArray.add(AlarmSignal(i.toLong() , "12:$i", true))
            }

        }

        val adapter = ClockAdapter(requireActivity(), clockArray)


        val recyclerView: RecyclerView? = wView.findViewById(R.id.alarms_list)
        recyclerView?.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView?.adapter = adapter

    }

}