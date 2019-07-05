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
import com.example.clockkotlin.activities.changeClock.ChangeClockActivity
import com.example.clockkotlin.databaseClockAlarm.AlarmSignal

/**
 * This class adds list of all clock on main screen.
 * User can use enable to active or inactive clock.
 * Or can click on time and change it.
 */
class ClockAdapter(private var mActivity: Activity, var alarms: ArrayList<AlarmSignal>) :
    RecyclerView.Adapter<ClockAdapter.AlarmHolderView>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): AlarmHolderView {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_view_item, viewGroup, false)
        return AlarmHolderView(view)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(alarmHolderView: AlarmHolderView, id: Int) {
        alarmHolderView.bind(alarms[id])
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
    inner class AlarmHolderView(itemView: View) : RecyclerView.ViewHolder(itemView), ClockAdapterContract.View {

        private var aSwitch: Switch = itemView.findViewById(R.id.alarm_switcher)
        private var textView: TextView = itemView.findViewById(R.id.alarm_time_text_view)
        private var id: Long = 0
        private var presenter: ClockAdapterContract.Presenter = ClockListPresenter(this)


        init {
            aSwitch.setOnClickListener { presenter.switchPressed(id, aSwitch.isChecked, textView.text.toString()) }
            textView.setOnClickListener { presenter.textViewPressed() }
        }


        /**
         * When user want change alarm time or delete clock, user pressed this button
         */
        override fun startChangeClockActivity() {
            val intent = Intent(mActivity, ChangeClockActivity::class.java)
            intent.putExtra("time", textView.text.toString())
            intent.putExtra("id", id)
            mActivity.startActivity(intent)
        }

        fun bind(alarm: AlarmSignal) {
            aSwitch.isChecked = alarm.enable
            textView.text = alarm.time
            id = alarm.id
        }

    }

}