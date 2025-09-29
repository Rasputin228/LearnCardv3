package com.example.v3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.v3.databinding.ActivityAddCardBinding

class AddCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCardBinding
    private var selectedImageUri: Uri? = null
    private val cardRepository by lazy { CardRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.saveButton.setOnClickListener {
            saveCard()
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.addImageButton.setOnClickListener {
            openImagePicker()
        }

        binding.removeImageButton.setOnClickListener {
            selectedImageUri = null
            binding.cardImageView.setImageResource(android.R.color.transparent)
        }
    }

    private fun saveCard() {
        val question = binding.questionInput.text.toString().trim()
        val answer = binding.answerInput.text.toString().trim()
        val category = binding.categoryInput.text.toString().trim()

        if (question.isEmpty() || answer.isEmpty()) {
            if (question.isEmpty()) {
                binding.questionInput.error = "Введите вопрос"
            }
            if (answer.isEmpty()) {
                binding.answerInput.error = "Введите ответ"
            }
            return
        }

        val newCard = Card(
            question = question,
            answer = answer,
            imageUrl = selectedImageUri?.toString(),
            category = if (category.isEmpty()) "Общее" else category
        )

        cardRepository.addCard(newCard)

        Toast.makeText(this, "Карточка добавлена!", Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            selectedImageUri?.let { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(binding.cardImageView)
            }
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 100
    }
}