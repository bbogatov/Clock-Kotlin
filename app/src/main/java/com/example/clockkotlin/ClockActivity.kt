package com.example.clockkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton

class ClockActivity : AppCompatActivity() {

    lateinit var closeClockImageButton: ImageButton
    lateinit var activeClockImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clock_activity)

        closeClockImageButton = findViewById(R.id.close_clock)
        activeClockImageButton = findViewById(R.id.active_clock)

        closeClockImageButton.setOnClickListener { backMainActivity() }
    }

    private fun backMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}