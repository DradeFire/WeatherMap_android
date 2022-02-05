package com.example.weathermap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.weathermap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()

    }

    fun setupFragment() {
        val navController = (supportFragmentManager.findFragmentById(R.id.frag_view)
                as NavHostFragment).navController
        setupActionBarWithNavController(navController)
    }

}