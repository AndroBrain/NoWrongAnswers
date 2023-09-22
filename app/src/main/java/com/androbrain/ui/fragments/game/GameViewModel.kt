package com.androbrain.ui.fragments.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.androbrain.core.viewmodel.SingleStateViewModel
import com.androbrain.data.game.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val SHOW_IN_APP_REVIEW_INDEX = 30

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gamesRepository: GamesRepository,
) : SingleStateViewModel<GameState>(
    savedStateHandle, GameState()
) {
    private val gameId = GameFragmentArgs.fromSavedStateHandle(savedStateHandle).game.id

    init {
        viewModelScope.launch {
            gamesRepository.getGameData(gameId).onEach { game ->
                updateState { state ->
                    state.copy(
                        game = game,
                        showInAppReview = game?.let { it.questionsIndex == SHOW_IN_APP_REVIEW_INDEX }
                            ?: false
                    )
                }
            }.launchIn(this)
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            state.value.game?.let { game ->
                val updatedGame = game.copy(questionsIndex = game.questionsIndex + 1)
                gamesRepository.updateGame(updatedGame)
                updateState { state -> state.copy(game = updatedGame) }
            }
        }
    }

    fun finishGame() {
        runBlocking {
            gamesRepository.deleteGame(gameId)
        }
    }

    fun inAppReviewShown() {
        updateState { state -> state.copy(showInAppReview = false) }
    }
}
