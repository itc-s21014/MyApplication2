package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class TaskSettingActivity3 : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var delete: ImageView
    private lateinit var edit: ImageView
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_setting3)

        name = findViewById(R.id.editTextTask)
        phone = findViewById(R.id.TestTime)
        delete = findViewById(R.id.imageView3)
        edit = findViewById(R.id.imageView4)

        db = DBHelper(this)

        name.setText(intent.getStringExtra("name"))
        phone.setText(intent.getStringExtra("phone"))

        delete.setOnClickListener {
            val intent = Intent(this, TaskSettingActivity::class.java)
            val names = name.text.toString()
            val deletedata = db.deleteuserdata(names)

            if (deletedata==true){
                Toast.makeText(this, "Delete Contact", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Not Delete", Toast.LENGTH_SHORT).show()
            }
            startActivity(intent)
        }

        edit.setOnClickListener {
            val intent = Intent(this, TaskSettingActivity::class.java)
            val names = name.text.toString()
            val numbers = phone.text.toString().toInt()
            val updatedata = db.updateuserdata(names, numbers)

            if (updatedata==true){
                Toast.makeText(this, "Update Contact", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Not Update", Toast.LENGTH_SHORT).show()
            }
            startActivity(intent)
        }
    }
}