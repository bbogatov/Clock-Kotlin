package com.example.clockkotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.widget.Switch
import android.widget.TextView

class ClockAdapter(activity: Activity, alarmsList: ArrayList<ClockAlarm>) : BaseAdapter() {

    private var activity = activity
    private var alarms = alarmsList
    private var inflater: LayoutInflater? = null


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView

        if (inflater == null) {
            inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = inflater?.inflate(R.layout.recycler_view_item, null)
        }


        val alarmTimeTextView = convertView?.findViewById(R.id.alarm_time_text_view) as TextView
        val alarmTimeSwitcher = convertView.findViewById(R.id.alarm_switcher) as Switch

        val clockAlarm: ClockAlarm = getItem(position)

        alarmTimeTextView.text = clockAlarm.time.text
        alarmTimeSwitcher.isChecked = clockAlarm.switch.isChecked

        return convertView
    }

    override fun getItem(position: Int): ClockAlarm {
        return this.alarms.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.alarms.size
    }

}