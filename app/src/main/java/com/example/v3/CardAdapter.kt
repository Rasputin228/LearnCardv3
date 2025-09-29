package com.example.v3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CardAdapter(
    private val onCardClick: (Card) -> Unit
) : ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(card)
        holder.itemView.setOnClickListener { onCardClick(card) }
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionText: TextView = itemView.findViewById(R.id.questionText)
        private val categoryText: TextView = itemView.findViewById(R.id.categoryText)
        private val cardImage: ImageView = itemView.findViewById(R.id.cardImage)
        private val learnedIndicator: View = itemView.findViewById(R.id.learnedIndicator)

        fun bind(card: Card) {
            questionText.text = card.question
            categoryText.text = card.category

            if (!card.imageUrl.isNullOrEmpty()) {
                cardImage.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(card.imageUrl)
                    .into(cardImage)
            } else {
                cardImage.visibility = View.GONE
            }

            val color = if (card.isLearned) {
                ContextCompat.getColor(itemView.context, android.R.color.holo_green_light)
            } else {
                ContextCompat.getColor(itemView.context, android.R.color.holo_red_light)
            }
            learnedIndicator.setBackgroundColor(color)
        }
    }
}

class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}