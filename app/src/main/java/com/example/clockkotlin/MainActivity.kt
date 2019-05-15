package com.example.clockkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    lateinit var clockImageButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clockImageButton = findViewById(R.id.clock_button)
        clockImageButton.setOnClickListener { createClockActivity() }
    }

    private fun createClockActivity() {
        val intent = Intent(this, ClockActivity::class.java)
        startActivity(intent)
    }
}
