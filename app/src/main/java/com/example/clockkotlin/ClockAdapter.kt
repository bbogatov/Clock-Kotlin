package com.example.clockkotlin

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Switch
import android.widget.TextView
import kotlin.collections.ArrayList

//Этот класс отображает на экране массив будильников
class ClockAdapter(private var activity: Activity, alarmsList: ArrayList<ClockAlarm>) : BaseAdapter() {

    private var alarms = alarmsList
    private var inflater: LayoutInflater? = null

    //Alarms DB
    private lateinit var clockDataBase: SQLiteDatabase
    private lateinit var dbHelper: DataBaseOpenHelper

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

        //Тут нужно будет менять включение выключение будильника

        alarmTimeTextView.text = clockAlarm.time.text
        alarmTimeSwitcher.isChecked = clockAlarm.switch.isChecked

        alarmTimeSwitcher.setOnClickListener { changeAlarm(clockAlarm) }

        //This code response for deleting and changing clock in DB

        /*
        TODO Так же тут нужно будет удалять alarmManager
         */
        convertView.setOnClickListener {
            val intent = Intent(activity, ChangeClockActivity::class.java)
            intent.putExtra("time", clockAlarm.time.text)
            intent.putExtra("id", clockAlarm.id)
            activity.startActivity(intent)
        }

        return convertView
    }


    /**
     *
     */
    private fun changeAlarm(clockAlarm: ClockAlarm) {
        clockAlarm.switch.isChecked = !clockAlarm.switch.isChecked

        dbHelper =
            DataBaseOpenHelper(activity.applicationContext, "clock_table_data_base", null, 1)
        clockDataBase = dbHelper.writableDatabase


        val contentValues = ContentValues()
        contentValues.put("time", clockAlarm.time.text.toString())
        contentValues.put("switch", clockAlarm.switch.isChecked)


        clockDataBase.update(
            "clock_table_data_base", contentValues, "id = ?",
            arrayOf(clockAlarm.id.toString())
        )


        dbHelper.close()
        clockDataBase.close()


        if (clockAlarm.switch.isChecked) {
            AlarmClockSignalArray.backAlarmSignal(clockAlarm.id)
        } else {
            AlarmClockSignalArray.removeAlarmSignal(clockAlarm.id)
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