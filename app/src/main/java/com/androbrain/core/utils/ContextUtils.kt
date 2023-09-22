package com.androbrain.core.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.androbrain.R
import com.yuyakaido.android.cardstackview.BuildConfig

const val APP_MARKET_LINK = "market://details?id=" + BuildConfig.APPLICATION_ID
const val APP_PAGE_LINK =
    "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
private const val DEV_ID = "6144141703629949077"
const val DEV_PAGE_LINK = "https://play.google.com/store/apps/dev?id=$DEV_ID"

fun Context.openLink(link: String, failLink: String? = null) {
    try {
        ContextCompat.startActivity(
            this,
            Intent(Intent.ACTION_VIEW, Uri.parse(link)),
            null
        )
    } catch (e: ActivityNotFoundException) {
        failLink?.let {
            ContextCompat.startActivity(
                this,
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(it),
                ),
                null
            )
        }
    }
}

fun Context.shareLink(link: String) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val appName = getString(R.string.app_name)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName)

        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        startActivity(Intent.createChooser(shareIntent, appName))
    } catch (e: Exception) {
        Toast.makeText(this, "Sorry, we couldn't open share screen", Toast.LENGTH_SHORT).show()
    }
}
