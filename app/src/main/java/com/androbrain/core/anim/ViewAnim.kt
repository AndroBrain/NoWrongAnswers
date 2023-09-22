package com.androbrain.core.anim

import android.view.View
import android.view.ViewPropertyAnimator

private const val ANIM_SCALE = 1.03F
private const val ANIM_SCALE_DURATION = 833L

fun View?.scaleUpAndDown(
    scaleToX: Float = ANIM_SCALE,
    scaleToY: Float = ANIM_SCALE,
) {
    this?.createScaleAnim(
        scaleX = scaleToX,
        scaleY = scaleToY,
    )?.withEndAction {
        createScaleAnim(scaleX = 1F, scaleY = 1F).withEndAction {
            scaleUpAndDown()
        }
    }
}

fun View.createScaleAnim(
    scaleX: Float = ANIM_SCALE,
    scaleY: Float = ANIM_SCALE,
    duration: Long = ANIM_SCALE_DURATION
): ViewPropertyAnimator = animate()
    .scaleX(scaleX)
    .scaleY(scaleY)
    .setDuration(duration)
