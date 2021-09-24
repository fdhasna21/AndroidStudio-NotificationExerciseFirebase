package com.tugas.notificationexercise.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.tugas.notificationexercise.databinding.ActivityShowAnimalBinding
import com.tugas.notificationexercise.dataclass.NotificationData
import java.util.ArrayList

class ShowAnimalActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShowAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")
        val data = intent.getParcelableExtra<NotificationData>("data")
        supportActionBar?.title = "Show Animal"

        binding.apply {
            layout.mainActivityButton.visibility = View.GONE
            layout.mainWelcome.visibility = View.GONE
            layout.mainRadioGroup.visibility = View.GONE
            layout.mainActivityDataInput.visibility = View.GONE

            val editableEditText : ArrayList<TextInputEditText> = arrayListOf(
                layout.mainTitle,
                layout.mainMessage,
                showAnimalName
            )
            editableEditText.forEach {
                it.isCursorVisible = false
                it.isFocusable = false
                it.isFocusableInTouchMode = false
            }

            layout.mainTitle.setText(title)
            layout.mainMessage.setText(message)
            showAnimalName.setText(data?.dataAnimal?.name)
//            Glide.with(this@ShowAnimalActivity)
//                .load()
//                .into(showAnimalPicture)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if(isTaskRoot){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            super.onBackPressed()
        }
    }
}