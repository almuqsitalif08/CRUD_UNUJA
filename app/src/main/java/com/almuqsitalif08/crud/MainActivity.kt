package com.almuqsitalif08.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.almuqsitalif08.crud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llsimpanData.setOnClickListener {
            startActivity(Intent(this@MainActivity, TambahDataActivity::class.java))
        }

        binding.lltampilData.setOnClickListener {
            startActivity(Intent(this@MainActivity, TampilDataActivity::class.java))
        }
    }
}