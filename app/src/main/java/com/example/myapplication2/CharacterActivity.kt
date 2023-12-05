package com.example.myapplication2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityCharacterBinding

class CharacterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterBinding
    private lateinit var charaTekipaki: Button
    private lateinit var charaNombiri: Button
    private var tekipakiClickCount = 0
    private var nombiriClickCount = 0
    private lateinit var dbHelper: DBHelper

    private var selectedCharacterId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        charaTekipaki = findViewById(R.id.charaTekipaki)
        charaNombiri = findViewById(R.id.charaNombiri)
        dbHelper = DBHelper(this)

        charaTekipaki.setOnClickListener {
            tekipakiClickCount++
            if (tekipakiClickCount % 2 == 0) {
                charaTekipaki.setBackgroundColor(Color.WHITE)
            } else {
                charaTekipaki.setBackgroundColor(Color.CYAN)
                selectedCharacterId = 1 // charaTekipakiに対応するID
                dbHelper.saveCharacterData(selectedCharacterId)
            }
        }

        charaNombiri.setOnClickListener {
            nombiriClickCount++
            if (nombiriClickCount % 2 == 0) {
                charaNombiri.setBackgroundColor(Color.WHITE)
            } else {
                charaNombiri.setBackgroundColor(Color.CYAN)
                selectedCharacterId = 2 // charaNombiriに対応するID
                dbHelper.saveCharacterData(selectedCharacterId)
            }
        }

        binding.charaFinished.setOnClickListener{
            val isDataSaved = dbHelper.saveCharacterData(selectedCharacterId)
            if (isDataSaved) {
                val characterData = dbHelper.getCharacterData(selectedCharacterId)
                if (characterData != null) {
                    if (characterData.moveToFirst()) {
                        do {
                            val id = characterData.getInt(0)
                            // 取得したデータを処理する（例：ログに出力する）
                            Log.d("CharacterActivity", "ID: $id")
                        } while (characterData.moveToNext())
                        characterData.close()
                    } else {
                        Log.d("CharacterActivity", "No data found")
                    }
                } else {
                    Log.d("CharacterActivity", "Cursor is null")
                }
            } else {
                Log.d("CharacterActivity", "Failed to save data")
            }
            val intent = Intent(this, TaskSettingActivity::class.java)
            val updatedata = dbHelper.updateAllCharacterIds(selectedCharacterId)
            if (updatedata==true){
                Toast.makeText(this, "性格を更新", Toast.LENGTH_SHORT).show()
            }
            intent.putExtra("id",selectedCharacterId)
            startActivity(intent)
        }

        binding.charaBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}