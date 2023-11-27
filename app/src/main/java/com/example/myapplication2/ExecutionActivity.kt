package com.example.myapplication2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityExecutionBinding
import android.widget.TextView
import kotlinx.coroutines.Runnable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExecutionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExecutionBinding
    private lateinit var timeTextView: TextView
    private lateinit var taskTextView: TextView
    private lateinit var updateTimeRunnable: Runnable
    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val handler = Handler(Looper.getMainLooper())
    private val taskList = arrayListOf(
        "起床",
        "食事",
        "お風呂",
        "着替え",
        "歯磨き",
        "髪のセット",
        "出発"
    )
    private var currentTaskIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExecutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timeTextView = binding.timeTextView
        updateTimeRunnable = Runnable { updateClock() }

        binding.resetBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        timeTextView = findViewById(R.id.timeTextView)

        taskTextView = findViewById(R.id.textView7)

        scheduleScreenTransition(this)

        displayCurrentTime()

        updateRealTime()

        updateTask()

//        runTextAnimation()

        val updateTimeDelayMillis: Int = 1000 * 60
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentTaskIndex = (currentTaskIndex + 1) % taskList.size
                updateTask()
                handler.postDelayed(this, updateTimeDelayMillis.toLong())
            }
        }, updateTimeDelayMillis.toLong())
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(updateTimeRunnable, 1000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateTimeRunnable)
    }

    private fun updateClock() {
        val currentTime = dateFormat.format(Date())
        timeTextView.text = currentTime
        handler.postDelayed(updateTimeRunnable, 1000)
    }

    private fun updateTask() {
        if (currentTaskIndex < taskList.size) {
            taskTextView.text = taskList[currentTaskIndex]
//            currentTaskIndex++
        } else {
            finish()
            val nextIntent = Intent(this@ExecutionActivity, EndActivity::class.java)
            startActivity(nextIntent)
        }
    }

/*    private fun getTaskForTime(time: String): String {
        for (pair in taskList) {
            if (pair.first == time) {
                return (pair.second)
            }
        }
        return "該当するタスクがありません"
    }
 */

/*    private fun runTextAnimation() {
        val textView = findViewById<TextView>(R.id.textView7)
        val screenWidth = resources.displayMetrics.widthPixels
        val textViewWidth = textView.width.toFloat()

        val distance = screenWidth + textViewWidth

        val duration = 10000L

        textView.translationX = -textViewWidth
        textView.animate()
            .translationXBy(distance)
            .setDuration(duration)
            .withEndAction { runTextAnimation() }
    }
 */

    private fun displayCurrentTime() {

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = sdf.format(Date())

        timeTextView.text = currentTime
    }

    private fun updateRealTime() {
        val timeRangeTextView = findViewById<TextView>(R.id.timeRangeTextView)
//        val textView7 = findViewById<TextView>(R.id.textView7)

        handler.post(object : Runnable {
            override fun run() {
                val currentTime = SimpleDateFormat("hh:mm", Locale.getDefault()).format(Date())

                timeRangeTextView.text = currentTime
//                textView7.text = getTaskForTime(currentTime)

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun scheduleScreenTransition(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EndActivity::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val triggerTimeMillis = System.currentTimeMillis() + (5 * 60 * 1000) // 5分後に遷移

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
    }
}