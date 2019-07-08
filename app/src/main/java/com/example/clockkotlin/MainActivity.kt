package com.example.clockkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.clockkotlin.fragments.addClockButtonFragment.AddClockButtonFragment
import com.example.clockkotlin.fragments.clockListFragment.ClockListFragment

/**
 * Main activity, from this activity application starts
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This fragment shows all clocks taken from DB
        val clockListFragment = ClockListFragment()
        val listFragment = supportFragmentManager.beginTransaction()
        listFragment.add(R.id.list_fragment, clockListFragment)
        listFragment.commit()


        val clockButtonFragment = AddClockButtonFragment()
        //this fragment shows AddNewClockButton
        val buttonFragment = supportFragmentManager.beginTransaction()
        buttonFragment.add(R.id.button_fragment, clockButtonFragment)
        buttonFragment.commit()
    }

}
