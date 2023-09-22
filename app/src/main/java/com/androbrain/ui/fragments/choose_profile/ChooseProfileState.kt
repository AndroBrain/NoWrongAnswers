package com.androbrain.ui.fragments.choose_profile

import com.androbrain.core.viewmodel.UiState
import com.androbrain.data.game.Game
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseProfileState(
    val games: List<Game> = emptyList()
) : UiState
