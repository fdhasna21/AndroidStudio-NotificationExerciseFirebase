package com.tugas.notificationexercise.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.tugas.notificationexercise.databinding.ActivityShowShapeBinding
import com.tugas.notificationexercise.dataclass.NotificationData
import java.util.ArrayList

class ShowShapeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShowShapeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowShapeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")
        val data = intent.getParcelableExtra<NotificationData>("data")
        supportActionBar?.title = "Calculate of Shape Area"

        binding.apply {
            layout.mainActivityButton.visibility = View.GONE
            layout.mainWelcome.visibility = View.GONE
            layout.mainRadioGroup.visibility = View.GONE
            layout.mainActivityDataInput.visibility = View.GONE

            val editableEditText : ArrayList<TextInputEditText> = arrayListOf(
                layout.mainTitle,
                layout.mainMessage,
                showShapeWidth,
                showShapeLength,
                showShapeArea
            )
            editableEditText.forEach {
                it.isCursorVisible = false
                it.isFocusable = false
                it.isFocusableInTouchMode = false
            }

            val width : Float = data?.dataShape?.width?.toFloat()!!
            val length : Float = data.dataShape?.length?.toFloat()!!

            layout.mainTitle.setText(title)
            layout.mainMessage.setText(message)
            showShapeWidth.setText(width.toString())
            showShapeLength.setText(length.toString())
            showShapeArea.setText((width * length).toString())
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