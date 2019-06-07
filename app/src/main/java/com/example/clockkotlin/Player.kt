package com.example.clockkotlin

import android.content.Context
import android.media.MediaPlayer

/**
 * This object response for playing music when clock should work
 */
object Player {
    private var player: MediaPlayer? = null

    /**
     * Initialize player
     */
    private fun init() {
        player = MediaPlayer.create(App.appContext, R.raw.nature_sounds)
    }

    /**
     * Plays music to wake up user
     */
    fun playMusic() {
        if (player == null) {
            init()
        }
        player?.isLooping = true
        player?.setOnCompletionListener {
            if (player!!.isLooping) {
                player?.seekTo(0)
            }
        }
        player?.start()
        Logger.log("Player is playing ")
    }

    /**
     * If user wakes up music stops, player removes
     */
    fun stopPlayer() {
        if (player != null) {
            player?.release()
            player = null
        }
        Logger.log("Player stopped")
    }
}