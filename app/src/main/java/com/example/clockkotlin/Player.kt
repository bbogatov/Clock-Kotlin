package com.example.clockkotlin

import android.content.Context
import android.media.MediaPlayer

/**
 * This object response for playing music when clock should work
 */
object Player {
    private var player: MediaPlayer? = null

    private fun init(context: Context) {
        player = MediaPlayer.create(context, R.raw.nature_sounds)
    }

    fun playMusic(context: Context) {
        if (player == null) {
            init(context)
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

    fun stopPlayer() {
        if (player != null) {
            player?.release()
            player = null
        }
        Logger.log("Player stopped")
    }
}