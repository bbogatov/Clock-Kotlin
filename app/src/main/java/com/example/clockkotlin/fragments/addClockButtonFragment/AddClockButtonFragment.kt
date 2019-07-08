package com.example.clockkotlin.fragments.addClockButtonFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.clockkotlin.R
import com.example.clockkotlin.activities.newClock.NewClockActivity

class AddClockButtonFragment : Fragment(), AddButtonContract.View {

    /**
     * Button that starts activity that adds new clock
     */
    private lateinit var addClockImageButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val presenter = AddButtonPresenter(this)

        val view = inflater.inflate(R.layout.fragment_new_clock_button, null)

        addClockImageButton = view.findViewById(R.id.clock_button)

        addClockImageButton.setOnClickListener { presenter.addClockButtonPressed() }

        return view
    }

    override fun showNewClockActivity() {
        val intent = Intent(context, NewClockActivity::class.java)
        startActivity(intent)
    }
}