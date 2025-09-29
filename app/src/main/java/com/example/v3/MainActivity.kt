package com.example.v3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.v3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardRepository: CardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardRepository = CardRepository(this)
        setupRecyclerView()
        setupClickListeners()
        loadCards()
        updateStatistics()
    }

    override fun onResume() {
        super.onResume()
        loadCards()
        updateStatistics()
    }

    private fun setupRecyclerView() {
        cardAdapter = CardAdapter { card ->
            showCardDetails(card)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cardAdapter
        }
    }

    private fun setupClickListeners() {
        binding.addCardButton.setOnClickListener {
            openAddCardScreen()
        }

        binding.studyButton.setOnClickListener {
            startStudySession()
        }
    }

    private fun loadCards() {
        val cards = cardRepository.getAllCards()
        cardAdapter.submitList(cards)
    }

    private fun updateStatistics() {
        val (total, learned) = cardRepository.getStatistics()
        binding.statsText.text = "Карточек: $total | Изучено: $learned"
    }

    private fun showCardDetails(card: Card) {
        Toast.makeText(this, "Карточка: ${card.question}", Toast.LENGTH_SHORT).show()
    }

    private fun openAddCardScreen() {
        val intent = Intent(this, AddCardActivity::class.java)
        startActivity(intent)
    }

    private fun startStudySession() {
        val cards = cardRepository.getAllCards()
        if (cards.isNotEmpty()) {
            val intent = Intent(this, StudyActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Сначала добавьте карточки!", Toast.LENGTH_SHORT).show()
        }
    }
}