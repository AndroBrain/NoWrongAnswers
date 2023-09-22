package com.androbrain.data.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.androbrain.data.game.GameSerializer
import com.androbrain.data.game.Games
import com.androbrain.data.game.GamesDataSource
import com.androbrain.data.game.GamesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private const val SHARED_PREFERENCES_NAME = "truth_or_date_preferences"
private const val GAME_DATA_FILE_NAME = "game-data.pb"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideGameDataStore(@ApplicationContext context: Context): DataStore<Games> =
        DataStoreFactory.create(
            serializer = GameSerializer,
            produceFile = { context.dataStoreFile(GAME_DATA_FILE_NAME) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )

    @Provides
    fun provideGamesRepository(
        @ApplicationContext context: Context,
        gamesDataSource: GamesDataSource,
    ) = GamesRepository(
        context = context,
        gamesDataSource = gamesDataSource,
    )
}
