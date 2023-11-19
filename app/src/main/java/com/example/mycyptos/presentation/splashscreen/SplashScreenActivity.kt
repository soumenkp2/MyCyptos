package com.example.mycyptos.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.example.mycyptos.R
import com.example.mycyptos.presentation.introscreen.IntroductionActivity

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_DELAY : Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieAnimationView)
        lottieAnimationView.setAnimation(R.raw.crypto_splash)
        lottieAnimationView.playAnimation()

        // Using a Handler to delay the transition to the main activity
        Handler().postDelayed({
            // Start the main activity after the splash delay
            val intent = Intent(this@SplashScreenActivity, IntroductionActivity::class.java)
            startActivity(intent)
            finish() // close the splash screen activity
        }, SPLASH_DELAY)
    }
}