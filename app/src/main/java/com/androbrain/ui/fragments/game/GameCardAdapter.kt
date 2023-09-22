package com.androbrain.ui.fragments.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.androbrain.databinding.ItemGameCardBinding

class GameCardAdapter(
    private var cards: List<String>,
) : RecyclerView.Adapter<GameCardAdapter.GameCard>() {
    class GameCard(val binding: ItemGameCardBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCard {
        val binding =
            ItemGameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameCard(binding)
    }

    override fun onBindViewHolder(holder: GameCard, position: Int) {
        val card = cards.getOrNull(position) ?: return
        with(holder.binding) {
            textViewDisplay.text = card
        }
    }

    fun setCards(cards: List<String>, changedItem: Int) {
        this.cards = cards
        notifyItemChanged(changedItem)
    }

    override fun getItemCount() = cards.size
}
