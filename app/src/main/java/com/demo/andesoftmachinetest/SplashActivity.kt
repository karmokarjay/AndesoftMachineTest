package com.demo.andesoftmachinetest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.demo.andesoftmachinetest.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 1200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        navigateToTheHomeScreen()
    }

    private fun navigateToTheHomeScreen() {
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}