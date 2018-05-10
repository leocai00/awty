package edu.washington.wanxic.arewethereyet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val message = findViewById<EditText>(R.id.message)
        val phone = findViewById<EditText>(R.id.phone)
        val time = findViewById<EditText>(R.id.time)
        val action = findViewById<Button>(R.id.start)

        action.isEnabled = false

        val watcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val validate1 = message.text.isNullOrEmpty()
                val validate2 = phone.text.isNullOrEmpty()
                val validate3 = time.text.isNullOrEmpty()
                if(!validate1 && !validate2 && !validate3) {
                    action.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //YOUR CODE
            }

            override fun afterTextChanged(s: Editable) {
                val validate1 = message.text.isNullOrEmpty()
                val validate2 = phone.text.isNullOrEmpty()
                val validate3 = time.text.isNullOrEmpty()
                if(validate1 || validate2 || validate3) {
                    action.isEnabled = false
                }
            }
        }

        message.addTextChangedListener(watcher)
        phone.addTextChangedListener(watcher)
        time.addTextChangedListener(watcher)

        time.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                var num = s.toString()
                if(num.startsWith("0")) {
                    time.setText("")
                } else if(num.startsWith("-")) {
                    time.setText("")
                }
            }
        })

        phone.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (phone.text.toString().length < 10) {
                    phone.error = "10 digit minimum!"
                }
            }
        })

        action.setOnClickListener {
            val intent = Intent(this, Alarm::class.java)

            if(action.text == "Start") {
                action.text = "Stop"
                Toast.makeText(this, "Alarm Started", Toast.LENGTH_SHORT).show()
                intent.putExtra("message", message.text.toString())
                intent.putExtra("phone", phone.text.toString())
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val nag = time.text.toString().toLong() * 60000
                Log.i("MainActivity", nag.toString())
                Log.i("MainActivity", System.currentTimeMillis().toString())
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), nag, pendingIntent)
            } else {
                action.text = "Start"
                Toast.makeText(this, "Alarm Stopped", Toast.LENGTH_SHORT).show()
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                alarmManager.cancel(pendingIntent)
            }
        }
    }
}
