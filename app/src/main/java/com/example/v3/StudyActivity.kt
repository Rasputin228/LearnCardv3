package com.example.v3

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.v3.databinding.ActivityStudyBinding

class StudyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyBinding
    private lateinit var currentCard: Card
    private val cardRepository by lazy { CardRepository(this) }
    private val cards = mutableListOf<Card>()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadCardsForStudy()
        setupClickListeners()
    }

    private fun loadCardsForStudy() {
        cards.clear()
        cards.addAll(cardRepository.getAllCards().filter { !it.isLearned })

        if (cards.isNotEmpty()) {
            showNextCard()
        } else {
            showNoCardsMessage()
        }
    }

    private fun showNextCard() {
        if (currentIndex < cards.size) {
            currentCard = cards[currentIndex]
            displayCard()
        } else {
            finishStudySession()
        }
    }

    private fun displayCard() {
        binding.questionText.text = currentCard.question
        binding.answerText.visibility = View.INVISIBLE
        binding.showAnswerButton.visibility = View.VISIBLE
        binding.knowButton.visibility = View.GONE
        binding.dontKnowButton.visibility = View.GONE

        if (!currentCard.imageUrl.isNullOrEmpty()) {
            binding.cardImage.visibility = View.VISIBLE
            Glide.with(this)
                .load(currentCard.imageUrl)
                .into(binding.cardImage)
        } else {
            binding.cardImage.visibility = View.GONE
        }

        updateProgress()
    }

    private fun setupClickListeners() {
        binding.showAnswerButton.setOnClickListener {
            binding.answerText.text = currentCard.answer
            binding.answerText.visibility = View.VISIBLE
            binding.showAnswerButton.visibility = View.GONE
            binding.knowButton.visibility = View.VISIBLE
            binding.dontKnowButton.visibility = View.VISIBLE
        }

        binding.knowButton.setOnClickListener {
            currentCard.isLearned = true
            cardRepository.updateCard(currentCard)
            currentIndex++
            showNextCard()
        }

        binding.dontKnowButton.setOnClickListener {
            currentIndex++
            showNextCard()
        }

        binding.closeButton.setOnClickListener {
            finish()
        }
    }

    private fun updateProgress() {
        binding.progressText.text = "${currentIndex + 1}/${cards.size}"
    }

    private fun showNoCardsMessage() {
        binding.questionText.text = "🎉 Все карточки изучены!"
        binding.answerText.visibility = View.GONE
        binding.cardImage.visibility = View.GONE
        binding.showAnswerButton.visibility = View.GONE
        binding.knowButton.visibility = View.GONE
        binding.dontKnowButton.visibility = View.GONE
        binding.progressText.text = "Готово!"
    }

    private fun finishStudySession() {
        Toast.makeText(this, "Сессия обучения завершена!", Toast.LENGTH_SHORT).show()
        finish()
    }
}