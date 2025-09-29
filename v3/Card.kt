package com.example.v3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    val id: Long = System.currentTimeMillis(),
    val question: String,
    val answer: String,
    val imageUrl: String? = null,
    val category: String = "Общее",
    var isLearned: Boolean = false
) : Parcelable