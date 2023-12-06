package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.example.myapplication2.databinding.ActivityTaskSetting2Binding
import com.google.android.material.textfield.TextInputEditText

class TaskSettingActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityTaskSetting2Binding
    private lateinit var name: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var save: Button
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskSetting2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        name = findViewById(R.id.textedit)
        phone = findViewById(R.id.textedit2)
        save = findViewById(R.id.button)
        db = DBHelper(this)

        val id = intent.getIntExtra("id", 1 or 2)

        save.setOnClickListener {
            val intent = Intent(this, TaskSettingActivity::class.java)
            val task = binding.textedit.text.toString()
            val names = name.text.toString()
            val numbers = phone.text.toString()

            if (TextUtils.isEmpty(names) || TextUtils.isEmpty(numbers)){
                Toast.makeText(this, "Add Name & Phone Number", Toast.LENGTH_SHORT).show()
            }
            else{
                val savedata = db.saveuserdata(names, numbers, id)
                if (savedata==true){
                    intent.putExtra("task", task)
                    Toast.makeText(this, "Save Contact", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Exist Contact", Toast.LENGTH_SHORT).show()
                }
            }
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }
}