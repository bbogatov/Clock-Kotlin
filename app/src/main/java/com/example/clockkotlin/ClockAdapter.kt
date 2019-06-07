package com.example.clockkotlin

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Switch
import android.widget.TextView
import kotlin.collections.ArrayList

/**
 * This class adds list of all clock on main screen.
 * User can use switch to active or inactive clock.
 * Or can click on time and change it.
 */
class ClockAdapter(private var activity: Activity, alarmsList: ArrayList<ClockAlarm>) : BaseAdapter() {

    private var alarms = alarmsList
    private var inflater: LayoutInflater? = null

    /**
     * Returns alarm signal with clock and switch for it.
     */
    override fun getView(position: Int, _convertView: View?, parent: ViewGroup?): View {

        //This is clicked clock that need change
        val clockAlarm: ClockAlarm = getItem(position)

        var convertView = _convertView

        if (inflater == null) {
            inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = inflater?.inflate(R.layout.recycler_view_item, null)
        }


        val alarmTimeTextView = convertView?.findViewById(R.id.alarm_time_text_view) as TextView

        val alarmTimeSwitcher = convertView.findViewById(R.id.alarm_switcher) as Switch


        alarmTimeTextView.text = clockAlarm.time.text
        alarmTimeSwitcher.isChecked = clockAlarm.switch.isChecked

        alarmTimeSwitcher.setOnClickListener { changeAlarmSwitch(clockAlarm) }

        //This code response for deleting and changing clock in DB
        convertView.setOnClickListener {
            val intent = Intent(activity, ChangeClockActivity::class.java)
            intent.putExtra("time", clockAlarm.time.text)
            intent.putExtra("id", clockAlarm.id)
            activity.startActivity(intent)
        }

        return convertView
    }

    /**
     * When user click on switch this code change makes clock active or inactive
     */
    private fun changeAlarmSwitch(clockAlarm: ClockAlarm) {
        clockAlarm.switch.isChecked = !clockAlarm.switch.isChecked

        val contentValues = ContentValues()
        contentValues.put("time", clockAlarm.time.text.toString())
        contentValues.put("switch", clockAlarm.switch.isChecked)
        LocalDataBase.changeAlarmSwitch(clockAlarm.id, contentValues)


        if (clockAlarm.switch.isChecked) {
            Alarms.backAlarm(clockAlarm.time.text.toString(), clockAlarm.id)
        } else {
            Alarms.removeAlarm(clockAlarm.time.text.toString(), clockAlarm.id)
        }
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