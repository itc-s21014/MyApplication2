package com.example.myapplication2

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityTimeSettingBinding
import java.util.Calendar

class TimeSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                    val selectedTime = "$selectedHour:$selectedMinute"
                    binding.button4.setText(selectedTime)
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
        }

        binding.start.setOnClickListener {
            val intent = Intent(this, ExecutionActivity::class.java)
            startActivity(intent)
        }
    }
}
