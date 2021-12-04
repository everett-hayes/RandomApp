package com.hayeseve.randomapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.hayeseve.randomapp.databinding.ActivityLogoBinding

class LogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler()

        val doNextActivity = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        object : Thread() {
            override fun run() {
                SystemClock.sleep(3000)
                handler.post(doNextActivity)
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()
        val showAnim = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        binding.logo.startAnimation(showAnim);
    }
}