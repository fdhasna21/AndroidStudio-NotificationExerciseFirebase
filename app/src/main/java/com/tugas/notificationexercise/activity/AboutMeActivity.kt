package com.tugas.notificationexercise.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tugas.notificationexercise.viewmodel.AboutMeActivityViewModel
import com.tugas.notificationexercise.BuildConfig
import com.tugas.notificationexercise.utils.IntentData
import com.tugas.notificationexercise.R
import com.tugas.notificationexercise.databinding.ActivityAboutMeBinding

class AboutMeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutMeBinding
    private lateinit var viewModel : AboutMeActivityViewModel
    private var intentData = IntentData(this)
    private var isConfigChange = false

    private fun setupToolbar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = getString(R.string.about_me)
    }

    private fun setupHeader(){
        binding.aboutEmail.text = BuildConfig.CREATOR_EMAIL
        binding.aboutDescription.text = listOf(getString(R.string.tab), getString(R.string.profile_description)).joinToString(" ")
        Glide.with(this)
            .load(R.drawable.profile_photo)
            .circleCrop()
            .into(binding.profilePicture)
    }

    private fun setupFindMe(){
        binding.aboutFindMe.itemIconTintList = null
        binding.aboutFindMe.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.about_whatsapp -> {
                    try{
                        val intent = Intent()
                        applicationContext.packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
                        intent.action = Intent.ACTION_VIEW
                        intent.type = "text/plain"
                        intent.data = Uri.parse(BuildConfig.CREATOR_WHATSAPP)
                        intent.setPackage("com.whatsapp")
                        startActivity(intent)
                    } catch (e : PackageManager.NameNotFoundException) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                    true }
                R.id.about_dicoding -> {
                    intentData.openBrowser(BuildConfig.CREATOR_DICODING)
                    true }
                R.id.about_email ->{
                    intentData.openEmail(BuildConfig.CREATOR_EMAIL)
                    true }
                R.id.about_github -> {
                    intentData.openBrowser("https://github.com/"+BuildConfig.CREATOR_GITHUB)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AboutMeActivityViewModel::class.java)
        if(!isConfigChange){
            setupToolbar()
            setupHeader()
            setupFindMe()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        viewModel.setConfiguration(newConfig.orientation)
        viewModel.getConfiguration().observe(this, {
            isConfigChange = (it != newConfig.orientation)
        })
        super.onConfigurationChanged(newConfig)
    }
}