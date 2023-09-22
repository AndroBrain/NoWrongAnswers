package com.androbrain.ui.fragments.game

import com.androbrain.core.viewmodel.UiState
import com.androbrain.data.game.Game
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameState(
    val game: Game? = null,
    val showInAppReview: Boolean = false,
    val finishShown: Boolean = false,
) : UiState
