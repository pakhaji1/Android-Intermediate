package com.example.appstorydicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.appstorydicoding.databinding.ActivitySplashScreenBinding
import com.example.appstorydicoding.local.UserPreferences
import com.example.appstorydicoding.local.UserToken
import com.example.appstorydicoding.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var userModel: UserToken
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        Handler(Looper.getMainLooper()).postDelayed({
            if (userModel.token == "") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, Const.delay)
    }
}