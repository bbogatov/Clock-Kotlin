package com.example.clockkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.clockkotlin.activities.AddNewClockActivity
import com.example.clockkotlin.adapter.ClockAdapter
import com.example.clockkotlin.database.LocalDataBase
import com.example.clockkotlin.fragments.addClockButtonFragment.AddClockButtonFragment
import com.example.clockkotlin.fragments.clockListFragment.ClockListFragment

/**
 * Main activity, from this activity application starts
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val clockListFragment = ClockListFragment()
        val clockButtonFragment = AddClockButtonFragment()


        val listFragment = supportFragmentManager.beginTransaction()
        listFragment.add(R.id.list_fragment, clockListFragment)
        listFragment.commit()


        val buttonFragment = supportFragmentManager.beginTransaction()
        buttonFragment.add(R.id.button_fragment, clockButtonFragment)
        buttonFragment.commit()
    }

    /**
     * Method shows screen where user can peek a time for new clock
     */
    private fun createNewClockActivity() {
        val intent = Intent(this, AddNewClockActivity::class.java)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalDataBase.closeDataBase()
    }
}
