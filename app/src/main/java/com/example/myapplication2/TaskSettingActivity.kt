package com.example.myapplication2

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskSettingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button: FloatingActionButton
    private lateinit var button2: Button
    private lateinit var button3: Button
    lateinit var dbh: DBHelper
    private lateinit var newArray: ArrayList<Datalist>
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_setting)

        recyclerView = findViewById(R.id.recycler)
        button = findViewById(R.id.floatingActionButton)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        val id = intent.getIntExtra("id", 1 or 2)

        button.setOnClickListener{
            intent = Intent(this, TaskSettingActivity2::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        button2.setOnClickListener {
            intent = Intent(this, SettingFinishedActivity::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            intent = Intent(this, CharacterActivity::class.java)
            startActivity(intent)
        }

        dbh = DBHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        displayuser()
    }

    private fun displayuser() {
        val newcursor: Cursor? = dbh.gettext()
        newArray = ArrayList<Datalist>()
        while (newcursor!!.moveToNext()){
            val uname = newcursor.getString(0)
            val unumber = newcursor.getString(1)
            newArray.add(Datalist(uname, unumber))
        }
        adapter = MyAdapter(newArray)
        recyclerView.adapter = adapter
        adapter.onItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@TaskSettingActivity, TaskSettingActivity3::class.java)
                intent.putExtra("name", newArray[position].name)
                intent.putExtra("phone", newArray[position].contact)
                startActivity(intent)
            }
        })
    }
}