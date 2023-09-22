package com.androbrain.core.sound

import android.content.Context
import androidx.annotation.RawRes

interface SoundManager {
    fun playConcurrentSound(context: Context, @RawRes soundId: Int)
}
