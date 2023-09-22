package com.androbrain.ui.fragments.choose_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.androbrain.core.viewmodel.SingleStateViewModel
import com.androbrain.data.game.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class ChooseProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gamesRepository: GamesRepository,
) : SingleStateViewModel<ChooseProfileState>(savedStateHandle, ChooseProfileState()) {

    init {
        viewModelScope.launch {
            gamesRepository.getAllGamesData().onEach { (games) ->
                updateState { state -> state.copy(games = games.sortedByDescending { game -> game.creationTime }) }
            }.launchIn(this)
        }
    }
}
