package com.example.mycyptos.presentation.introscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycyptos.MainActivity
import com.example.mycyptos.databinding.ActivityIntroductionBinding

class IntroductionActivity : AppCompatActivity() {

    private var binding : ActivityIntroductionBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.tvProceed.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // close the splash screen activity
        }

    }
}