package com.androbrain.data.settings

import com.androbrain.data.settings.lightning.LightningDataSource
import com.androbrain.data.settings.lightning.LightningMode
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val lightningDataSource: LightningDataSource,
) {
    fun getLightningMode() = lightningDataSource.getLightningMode()

    fun setLightningMode(lightningMode: LightningMode) {
        lightningDataSource.setLightningMode(lightningMode)
    }
}
