package com.example.mycyptos

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.mycyptos.databinding.ActivityMainBinding
import com.example.mycyptos.databinding.FragmentTopCryptoBinding
import com.example.mycyptos.presentation.favcrypto.FavCryptoFragment
import com.example.mycyptos.presentation.searchcrypto.SearchCryptoFragment
import com.example.mycyptos.presentation.topcrypto.TopCryptoFragment
import com.example.mycyptos.utils.NetworkReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationBar,navController)

        val bottomNavigationBar: BottomNavigationView = findViewById(R.id.bottomNavigationBar)

        binding.bottomNavigationBar.isActivated = true
        Log.d("BOTTOM NAV", binding.bottomNavigationBar.selectedItemId.toString() + " " + binding.bottomNavigationBar.isActivated.toString())

        binding.bottomNavigationBar.setOnClickListener {
            Log.d("BOTTOM NAV", binding.bottomNavigationBar.id.toString() + " " + binding.bottomNavigationBar.isActivated.toString())
        }

        bottomNavigationBar.setOnItemSelectedListener {

            Log.d("Item Id",it.itemId.toString())

            when (it.itemId) {
                R.id.topCryptoFragment_menu -> {
                    Log.d("Item Id","topcryptofragment")
                    loadFragment(TopCryptoFragment())
                    true
                }
                R.id.favCryptoFragment_menu -> {
                    Log.d("Item Id","favcryptofragment")
                    loadFragment(FavCryptoFragment())
                    true
                }
                R.id.searchCryptoFragment_menu -> {
                    Log.d("Item Id","searchcryptofragment")
                    loadFragment(SearchCryptoFragment())
                    true
                }

                else -> {
                    Log.d("Item Id","else item")
                    true}
            }
        }

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.NavHostFragment,fragment)
        transaction.commit()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }
}