package com.androbrain.data.game

import android.content.Context
import com.androbrain.R
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val gamesDataSource: GamesDataSource,
    private val context: Context,
) {
    fun getGameData(id: String) = gamesDataSource.getGameData(id)

    fun getAllGamesData() = gamesDataSource.getAllGamesData()

    suspend fun createNewGame(): Game {
        val game = Game(questions = context.resources.getStringArray(R.array.questions).toList())
        gamesDataSource.addNewGame(game)
        return game
    }

    suspend fun deleteGame(id: String) {
        gamesDataSource.deleteGame(id)
    }

    suspend fun updateGame(game: Game) {
        gamesDataSource.updateGame(game)
    }
}
