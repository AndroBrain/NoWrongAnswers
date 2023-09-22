package com.androbrain.core.sound

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

class MediaPlayerSoundManager : SoundManager {
    override fun playConcurrentSound(context: Context, @RawRes soundId: Int) {
        var mediaPlayer: MediaPlayer? = MediaPlayer.create(context, soundId)
        mediaPlayer?.apply {
            setOnCompletionListener {
                reset()
                release()
                mediaPlayer = null
            }
            start()
        }
    }
}
