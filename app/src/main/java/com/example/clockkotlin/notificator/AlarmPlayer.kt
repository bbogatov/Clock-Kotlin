package com.example.clockkotlin.notificator

import android.media.MediaPlayer
import com.example.clockkotlin.ClockApplication
import com.example.clockkotlin.logger.Logger
import com.example.clockkotlin.R

/**
 * This object response for playing music when clock should work
 */
object AlarmPlayer {
    private var player: MediaPlayer? = null

    /**
     * Initialize player
     */
    private fun getInstance() {
        player = MediaPlayer.create(
            ClockApplication.applicationContext(),
            R.raw.nature_sounds
        )
    }

    /**
     * Plays music to wake up user
     */
    fun playMusic() {
        if (player == null) {
            getInstance()
        }
        player?.isLooping = true
        player?.setOnCompletionListener {
            if (player!!.isLooping) {
                player?.seekTo(0)
            }
        }
        player?.start()
        Logger.log("AlarmPlayer is playing ")
    }

    /**
     * If user wakes up music stops, player removes
     */
    fun stopPlayer() {
        if (player != null) {
            player?.release()
            player = null
        }
        Logger.log("AlarmPlayer stopped")
    }
}