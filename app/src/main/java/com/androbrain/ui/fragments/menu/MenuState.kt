package com.androbrain.ui.fragments.menu

import com.androbrain.core.viewmodel.UiState
import com.androbrain.data.game.Game
import com.androbrain.data.settings.lightning.LightningMode
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuState(
    val lightningMode: LightningMode = LightningMode.SYSTEM,
    val canContinue: Boolean = false,
    val games: List<Game> = emptyList(),
    val game: Game? = null,
) : UiState