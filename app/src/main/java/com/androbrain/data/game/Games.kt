package com.androbrain.data.game

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Games(
    val games: List<Game> = emptyList()
)
