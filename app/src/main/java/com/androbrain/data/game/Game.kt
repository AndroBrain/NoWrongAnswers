package com.androbrain.data.game

import android.os.Parcelable
import androidx.annotation.Keep
import java.util.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class Game(
    @SerialName("id") val id: String = UUID.randomUUID().toString(),
    @SerialName("questions") val questions: List<String>,
    @SerialName("question_index") val questionsIndex: Int = 0,
    @SerialName("last_edit_time") val lastEditTime: Long = Date().time,
) : Parcelable
