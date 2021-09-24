package com.tugas.notificationexercise.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.tugas.notificationexercise.databinding.ActivityShowNotificationBinding
import java.util.ArrayList

class ShowNotificationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShowNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")
        val from = intent.getStringExtra("from")
        supportActionBar?.title = "Show Message From $from"

        binding.layout.apply {
            mainActivityButton.visibility = View.GONE
            mainWelcome.visibility = View.GONE
            mainRadioGroup.visibility = View.GONE
            mainActivityDataInput.visibility = View.GONE

            val editableEditText : ArrayList<TextInputEditText> = arrayListOf(
                mainTitle,
                mainMessage
            )
            editableEditText.forEach {
                it.isCursorVisible = false
                it.isFocusable = false
                it.isFocusableInTouchMode = false
            }

            mainTitle.setText(title)
            mainMessage.setText(message)
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