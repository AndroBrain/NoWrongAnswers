package com.androbrain.core.utils

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory

fun Activity.showInAppReview(onComplete: (() -> Unit)? = null) {
    val manager = ReviewManagerFactory.create(this)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val reviewInfo = task.result
            val flow = manager.launchReviewFlow(this, reviewInfo)
            flow.addOnCompleteListener { onComplete?.invoke() }
        }
    }
}
