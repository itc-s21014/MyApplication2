package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hmToSetting.setOnClickListener{
            val intent = Intent(this, TaskSettingActivity::class.java)
            startActivity(intent)
        }

        binding.hmStart.setOnClickListener{
            val intent = Intent(this, TimeSettingActivity::class.java)
            startActivity(intent)
        }

        binding.hmToHowToUse.setOnClickListener{
            val intent = Intent(this, HowToUseActivity::class.java)
            startActivity(intent)
        }
    }
}