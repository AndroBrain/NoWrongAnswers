package com.androbrain.ui.fragments

import com.airbnb.epoxy.EpoxyController
import com.androbrain.R
import com.androbrain.data.game.Game
import com.androbrain.ui.models.ProfileModel
import java.text.DateFormat
import java.util.*

class ChooseProfileController(
    private val onProfileClick: (Game) -> Unit
) : EpoxyController() {
    private val dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)

    var profiles: List<Game>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        profiles?.forEachIndexed { index, profile ->
            ProfileModel(
                createTitle = { context ->
                    context.getString(
                        R.string.profile,
                        profile.questionsIndex + 1,
                        context.resources.getStringArray(R.array.questions).size,
                    )
                },
                createdAt = dateFormatter.format(Date(profile.creationTime)),
                onClick = { onProfileClick(profile) }
            ).id(profile.id).addTo(this)
        }
    }
}
