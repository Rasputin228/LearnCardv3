package com.example.v3

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CardRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("cards_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "cards_list"

    fun addCard(card: Card) {
        val cards = getAllCards().toMutableList()
        cards.add(card)
        saveCards(cards)
    }

    fun updateCard(updatedCard: Card) {
        val cards = getAllCards().toMutableList()
        val index = cards.indexOfFirst { it.id == updatedCard.id }
        if (index != -1) {
            cards[index] = updatedCard
            saveCards(cards)
        }
    }

    fun getAllCards(): List<Card> {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            val type = object : TypeToken<List<Card>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun getStatistics(): Pair<Int, Int> {
        val cards = getAllCards()
        val total = cards.size
        val learned = cards.count { it.isLearned }
        return Pair(total, learned)
    }

    private fun saveCards(cards: List<Card>) {
        val json = gson.toJson(cards)
        sharedPreferences.edit().putString(key, json).apply()
    }
}