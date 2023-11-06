package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivitySettingFinishedBinding

class SettingFinishedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingFinishedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sfToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.sfToUse.setOnClickListener {
            val intent = Intent(this, TimeSettingActivity::class.java)
            startActivity(intent)
        }
    }
}