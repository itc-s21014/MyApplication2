package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

class TaskSettingActivity3 : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var phone: Spinner
    private lateinit var delete: ImageView
    private lateinit var edit: ImageView
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_setting3)

        name = findViewById(R.id.editTextTask)
        phone = findViewById(R.id.spinner)
        delete = findViewById(R.id.imageView3)
        edit = findViewById(R.id.imageView4)

        db = DBHelper(this)

        name.setText(intent.getStringExtra("name"))

        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            phone.adapter = adapter
        }

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
            val selectedPhone = phone.selectedItem.toString()
            val numbers = selectedPhone.split("分")[0].toIntOrNull() ?: 0
            val updatedata = db.updateuserdata(names, numbers)

            if (updatedata==true){
                Toast.makeText(this, "Update Contact", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Not Update", Toast.LENGTH_SHORT).show()
            }
            startActivity(intent)
        }
        val initialPhone = intent.getIntExtra("phone", 1)
        val position = db.findPositionForPhone(initialPhone)
        phone.setSelection(position)
    }
}