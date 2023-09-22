package com.androbrain.data.settings.lightning

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private const val KEY_LIGHTNING_MODE = "LIGHTNING_MODE"

class LightningDataSource @Inject constructor(private val preferences: SharedPreferences) {

    private val _lightningMode: MutableStateFlow<LightningMode> = MutableStateFlow(
        LightningMode.valueOf(
            preferences.getString(
                KEY_LIGHTNING_MODE,
                LightningMode.DARK.name
            ) ?: LightningMode.DARK.name
        )
    )

    fun getLightningMode(): StateFlow<LightningMode> = _lightningMode

    fun setLightningMode(dayNightMode: LightningMode) {
        _lightningMode.update { dayNightMode }
        preferences.edit {
            putString(KEY_LIGHTNING_MODE, dayNightMode.name)
        }
    }
}
