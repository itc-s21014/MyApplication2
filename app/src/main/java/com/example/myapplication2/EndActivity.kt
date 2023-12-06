package com.example.myapplication2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.myapplication2.databinding.ActivityEndBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EndActivity : AppCompatActivity(){
    private lateinit var binding: ActivityEndBinding
    private lateinit var textViewTime: TextView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textViewTime = findViewById(R.id.textViewTime)

        handler.post(object : Runnable {
            override fun run() {
                updateCurrentTime()
                handler.postDelayed(this, 1000)
            }
        })

        showNotificationWithSound(this, "次の準備", "新しいメッセージが届きました。")

        binding.end.setOnClickListener {
            handler.removeCallbacksAndMessages(null) // 画面が固定されるときにハンドラーを停止
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null) // アクティビティが非アクティブになるときにもハンドラーを停止
    }

    private fun updateCurrentTime() {
        val currentTime = getCurrentTime()
        textViewTime.text = currentTime
    }

    private fun getCurrentTime(): String {
        val currentTime = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return simpleDateFormat.format(Date(currentTime))
    }

    fun showNotificationWithSound(context: Context, title: String, content: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel"
            val channelName = "Default Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, "default_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(soundUri)

        notificationManager.notify(1, builder.build())
    }
}