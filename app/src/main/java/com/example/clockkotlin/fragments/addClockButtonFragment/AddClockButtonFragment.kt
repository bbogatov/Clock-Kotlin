package com.example.clockkotlin.fragments.addClockButtonFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.clockkotlin.R
import com.example.clockkotlin.logger.Logger

class AddClockButtonFragment : Fragment() {

    /**
     * Button that starts activity that adds new clock
     */
    private lateinit var addClockImageButton: ImageButton




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_new_clock_button, null)

        addClockImageButton = view.findViewById(R.id.clock_button)

        addClockImageButton.setOnClickListener { clicks() }


        return view

    }

    fun clicks() {

    }

}