package com.example.myapplication2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityExecutionBinding
import android.widget.TextView
import kotlinx.coroutines.Runnable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExecutionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExecutionBinding
    private lateinit var timeTextView: TextView
    private lateinit var timeRangeTextView: TextView
    private lateinit var taskTextView: TextView
    private lateinit var updateTimeRunnable: Runnable
    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val handler = Handler(Looper.getMainLooper())
//    val taskList: ArrayList<String> = ArrayList()
    private lateinit var dbh: DBHelper
    private lateinit var newArray: ArrayList<Datalist>
    private var currentTaskIndex = 0
    private var shouldUpdate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExecutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timeTextView = binding.timeTextView
        timeRangeTextView = findViewById(R.id.timeRangeTextView)
        updateTimeRunnable = Runnable { updateClock() }

        binding.resetBtn.setOnClickListener {
            shouldUpdate = false
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        timeTextView = findViewById(R.id.timeTextView)

        taskTextView = findViewById(R.id.textView7)

        val task = intent.getStringExtra("task")

        if (task != null) {
            taskTextView.text = task
        }

        scheduleScreenTransition(this)

        displayCurrentTime()

//        updateRealTime()

        dbh = DBHelper(this)

        displayuser()

        updateTask()

//        runTextAnimation()

/*        val updateTimeDelayMillis: Int = 1000 * 10
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentTaskIndex = (currentTaskIndex + 1) // % taskList.size
                updateTask()
                handler.postDelayed(this, updateTimeDelayMillis.toLong())
            }
        }, updateTimeDelayMillis.toLong())
 */
    }

    private fun displayuser() {
        val newcursor: Cursor? = dbh.gettext()
        newArray = ArrayList()
        while (newcursor!!.moveToNext()) {
            val uname = newcursor.getString(0)
            val unumber = newcursor.getInt(1)
            newArray.add(Datalist(uname, unumber))
        }
        Log.d("nandemo iiyo", newArray.toString())
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(updateTimeRunnable, 0)
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
        Log.d("UPDATE TASK", "currentTaskIndex: ${currentTaskIndex}")
//        currentTaskIndex++
//        handler.post(object : Runnable {
//            override fun run() {
        if (shouldUpdate) {
            if (currentTaskIndex < newArray.size) {
                val task = newArray[currentTaskIndex]
                val taskText = "${task.name}"
                taskTextView.text = taskText
                val minutesFromDatabase = task.contact
                timeRangeTextView.text = "${minutesFromDatabase}"
                val timeRangeTextView = findViewById<TextView>(R.id.timeRangeTextView)
                val currentTime = Calendar.getInstance()
                val endCurrentTime = Calendar.getInstance()
//            endCurrentTime.add(Calendar.MINUTE, minutesFromDatabase)
                endCurrentTime.add(Calendar.MINUTE, minutesFromDatabase)
                timeRangeTextView.text =
                    "${dateFormat.format(currentTime.time)} - ${dateFormat.format(endCurrentTime.time)}"
//            handler.postDelayed(::updateTask, minutesFromDatabase * 60 * 1000L)
                handler.postDelayed(::updateTask, minutesFromDatabase * 60 * 1000L)

                /*            if (newArray.size > currentTaskIndex) {
                            val datalist = newArray[currentTaskIndex]
                        }
             */
//                    handler.postDelayed(updateTimeRunnable, 1000)
            }
            if (currentTaskIndex == newArray.size) {
                finish()
                val nextIntent = Intent(this@ExecutionActivity, EndActivity::class.java)
                startActivity(nextIntent)
//                    handler.postDelayed(this, 1000)
            }
            currentTaskIndex++
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

/*    private fun updateRealTime() {
    //        val textView7 = findViewById<TextView>(R.id.textView7)

        handler.post(object : Runnable {
            override fun run() {
                val currentTime = Calendar.getInstance()
//                currentTime.time = DateFormat("HH:mm:ss", Locale.getDefault())
                val endCurrentTime = Calendar.getInstance()
                endCurrentTime.add(0, 5)
                timeRangeTextView.text = "${dateFormat.format(currentTime.time)} - ${dateFormat.format(endCurrentTime.time)}"
    //                textView7.text = getTaskForTime(currentTime)

                handler.postDelayed(this, 1000)
            }
        })
    }
 */

    private fun scheduleScreenTransition(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EndActivity::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val triggerTimeMillis = System.currentTimeMillis() + (5 * 60 * 1000) // 5分後に遷移

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
    }
}