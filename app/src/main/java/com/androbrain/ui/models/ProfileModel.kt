package com.androbrain.ui.models

import android.content.Context
import com.androbrain.R
import com.androbrain.core.uimodel.ViewBindingModel
import com.androbrain.databinding.ItemProfileBinding

class ProfileModel(
    private val createTitle: (Context) -> String,
    private val createdAt: String,
    private val onClick: () -> Unit,
) : ViewBindingModel<ItemProfileBinding>(R.layout.item_profile) {
    override fun ItemProfileBinding.bind() {
        textViewTitle.text = createTitle(root.context)
        textViewSubtitle.text = root.context.getString(R.string.profile_created_at, createdAt)
        image.contentDescription = "${textViewTitle.text} ${textViewSubtitle.text}"
        root.setOnClickListener { onClick() }
    }
}
