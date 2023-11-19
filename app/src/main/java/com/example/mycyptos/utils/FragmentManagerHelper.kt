package com.example.mycyptos.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mycyptos.R
import javax.inject.Inject


class FragmentManagerHelper : AppCompatActivity() {

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.NavHostFragment,fragment)
        transaction.commit()
    }
}