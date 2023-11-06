package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityCharacterBinding

class CharacterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.charaFinished.setOnClickListener{
            val intent = Intent(this,SettingFinishedActivity::class.java)
            startActivity(intent)
        }

        binding.charaBack.setOnClickListener {
            val intent = Intent(this, TaskSettingActivity::class.java)
            startActivity(intent)
        }
    }
}