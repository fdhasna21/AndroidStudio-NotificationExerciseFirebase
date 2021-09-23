package com.tugas.notificationexercise.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import com.tugas.notificationexercise.R
import com.tugas.notificationexercise.databinding.ActivityMainBinding
import com.tugas.notificationexercise.dataclass.PushNotification
import com.tugas.notificationexercise.dataclass.PushNotificationResponse
import com.tugas.notificationexercise.utils.ServerAPI
import com.tugas.notificationexercise.utils.ServerInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private var isNotEmpty : Boolean = false
    set(value) {
        binding.mainActivityButton.isEnabled = value
        field = value
    }
    companion object{
        const val TAG = "FirebaseMessaging"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isNotEmpty = false
        binding.mainMessage.addTextChangedListener(this)
        binding.mainTitle.addTextChangedListener(this)
        binding.mainActivityButton.setOnClickListener(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful){
                val msg = "Token ${it.result}"
                Log.i(TAG, msg)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Fetching FCM registration token filed", it.exception)
            }

        }

        FirebaseMessaging.getInstance().subscribeToTopic("all").addOnCompleteListener {
            if(it.isSuccessful){
                Log.i(TAG, "Successfully send to all")
            } else {
                Log.i(TAG, "Subscription notification failed")
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        isNotEmpty = binding.mainTitle.text.toString().count() > 0 && binding.mainMessage.text.toString().count() > 0
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun onClick(v: View?) {
        when(v){
            binding.mainActivityButton -> {
                binding.mainProgress.visibility = View.VISIBLE
                binding.mainActivityButton.isEnabled = false
                val message = PushNotification(
                    notification = PushNotification.NotificationData(
                        title = binding.mainTitle.text.toString(),
                        message = binding.mainMessage.text.toString()
                    )
                )
                Log.i(TAG, "sendMessage: $message")

                val serverAPI = ServerAPI().getServerAPI().create(ServerInterface::class.java)
                serverAPI.sendNotification(message).enqueue(object : Callback<PushNotificationResponse>{
                    override fun onResponse(call: Call<PushNotificationResponse>, response: Response<PushNotificationResponse>) {
                        binding.mainProgress.visibility = View.GONE
                        if(response.isSuccessful){
                            Log.i(TAG, "onResponseSuccess: ${response.body()}")
                            binding.mainTitle.text = null
                            binding.mainMessage.text = null
                            binding.mainActivityButton.isEnabled = true
                            Toast.makeText(this@MainActivity, "Message sent!", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.i(TAG, "onResponseNotSuccess: ${response.code()} ${response.errorBody()}")
                            Toast.makeText(this@MainActivity, "Error ${response.code()}. Please try again later.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PushNotificationResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
                        Log.i(TAG, "onFailure: $t")
                        binding.mainProgress.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "Error : $t", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                startActivity(
                    Intent(
                        this,
                        AboutMeActivity::class.java
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}