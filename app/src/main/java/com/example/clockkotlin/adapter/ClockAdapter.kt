package com.example.clockkotlin.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.clockkotlin.R
import com.example.clockkotlin.activities.ChangeClockActivity
import com.example.clockkotlin.databaseClockAlarm.AlarmSignal
import com.example.clockkotlin.logger.Logger

/**
 * This class adds list of all clock on main screen.
 * User can use enable to active or inactive clock.
 * Or can click on time and change it.
 */
class ClockAdapter(private val activity: Activity, var alarms: ArrayList<AlarmSignal>) :
    RecyclerView.Adapter<ClockAdapter.AlarmHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): AlarmHolder {
        Logger.log("Запускаем onCreateViewHolder")
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_view_item, viewGroup, false)
        return AlarmHolder(view)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(alarmHolder: AlarmHolder, id: Int) {
        Logger.log("Запускаем байн")
        alarmHolder.bind(alarms[id])
    }

    /**
     * This class takes array of clocks and shows them on screen
     *
     * @param clockAlarms array of clocks
     */
    fun setClockAlarms(clockAlarms: ArrayList<AlarmSignal>) {
        this.alarms = clockAlarms
        notifyDataSetChanged()
    }


    /**
     * Class that add one clock element to the screen
     */
    inner class AlarmHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClockAdapterContract.View {

        private var aSwitch: Switch = itemView.findViewById(R.id.alarm_switcher)
        private var textView: TextView = itemView.findViewById(R.id.alarm_time_text_view)
        private var index: Long = 0
        private var presenter: ClockAdapterContract.Presenter = ClockListPresenter(this)



        init {
            Logger.log("Запускаем inner")
            aSwitch.setOnClickListener { presenter.switchPressed(index, aSwitch.isChecked, textView.text.toString()) }
            textView.setOnClickListener { presenter.textViewPressed() }
        }


        /**
         * When user want change alarm time or delete clock, user pressed this button
         */
        override fun changeAlarmTime() {
            val intent = Intent(activity, ChangeClockActivity::class.java)
            intent.putExtra("time", textView.text.toString())
            intent.putExtra("index", index)
            intent.putExtra("enable", aSwitch.isChecked)
            activity.startActivity(intent)
        }

        fun bind(alarm: AlarmSignal) {
            aSwitch.isChecked = alarm.enable
            textView.text = alarm.time
            index = alarm.id
        }

    }

}