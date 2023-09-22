package com.androbrain.data.game

import androidx.datastore.core.DataStore
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class GamesDataSource @Inject constructor(
    private val gamesDataStore: DataStore<Games>,
) {

    fun getGameData(id: String) = gamesDataStore.data
        .map { (games) -> games.find { it.id == id } }

    fun getAllGamesData() = gamesDataStore.data

    suspend fun addNewGame(game: Game) {
        gamesDataStore.updateData { (games) ->
            val gameUnderTheSameId = games.find { it.id == game.id }
            if (gameUnderTheSameId != null) {
                Games(games - gameUnderTheSameId + game)
            } else {
                Games(games + game)
            }
        }
    }

    suspend fun deleteGame(id: String) {
        gamesDataStore.updateData { (games) ->
            val mutableGames = games.toMutableList()
            val gameToRemoveIndex = games.indexOfFirst { it.id == id }
            if (gameToRemoveIndex < 0) {
                return@updateData Games(mutableGames)
            }
            mutableGames.removeAt(gameToRemoveIndex)
            Games(mutableGames)
        }
    }

    suspend fun updateGame(game: Game) {
        gamesDataStore.updateData { (games) ->
            val mutableGames = games.toMutableList()
            val copyOfGame = mutableGames.find { it.id == game.id }
            mutableGames.remove(copyOfGame)
            Games(mutableGames + game)
        }
    }
}
