package com.example.myapplication2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityCharacterBinding

class CharacterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterBinding
    private lateinit var charaTekipaki: Button
    private lateinit var charaNombiri: Button
    private var tekipakiClickCount = 0
    private var nombiriClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        charaTekipaki = findViewById(R.id.charaTekipaki)
        charaNombiri = findViewById(R.id.charaNombiri)

        charaTekipaki.setOnClickListener {
            tekipakiClickCount++
            if (tekipakiClickCount % 2 == 0) {
                charaTekipaki.setBackgroundColor(Color.WHITE)
            } else {
                charaTekipaki.setBackgroundColor(Color.CYAN)
            }
        }

        charaNombiri.setOnClickListener {
            nombiriClickCount++
            if (nombiriClickCount % 2 == 0) {
                charaNombiri.setBackgroundColor(Color.WHITE)
            } else {
                charaNombiri.setBackgroundColor(Color.CYAN)
            }
        }

        binding.charaFinished.setOnClickListener{
            val intent = Intent(this,TaskSettingActivity::class.java)
            startActivity(intent)
        }

        binding.charaBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}