package com.androbrain.ui.fragments.menu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.androbrain.core.viewmodel.SingleStateViewModel
import com.androbrain.data.game.GamesRepository
import com.androbrain.data.settings.SettingsRepository
import com.androbrain.data.settings.lightning.LightningMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class MenuViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val settingsRepository: SettingsRepository,
    private val gamesRepository: GamesRepository,
//    private val analyticsRepository: AnalyticsRepository,
) : SingleStateViewModel<MenuState>(savedStateHandle, MenuState()) {
    init {
        viewModelScope.launch {
            settingsRepository.getLightningMode().onEach { lightningMode ->
                updateState { state -> state.copy(lightningMode = lightningMode) }
            }.launchIn(this)

            gamesRepository.getAllGamesData().onEach { allGames ->
                updateState { state ->
                    state.copy(
                        canContinue = allGames.games.isNotEmpty(),
                        games = allGames.games.sortedBy { it.creationTime },
                    )
                }
            }.launchIn(this)
        }
    }

    fun setLightningMode(lightningMode: LightningMode) {
        settingsRepository.setLightningMode(lightningMode)
    }

    fun createGame() {
        viewModelScope.launch {
            val game = gamesRepository.createNewGame()
            updateState { state -> state.copy(game = game) }
        }
    }

    fun gameOpened() {
        updateState { state -> state.copy(game = null) }
    }
}