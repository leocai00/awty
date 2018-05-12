package edu.washington.wanxic.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import android.app.PendingIntent
import android.app.AlarmManager


class Alarm : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val smsManager = SmsManager.getDefault()

        val message = intent!!.getStringExtra("message")
        val phone = intent!!.getStringExtra("phone")
        val phoneNum = "(" + phone.substring(0, 3) + ")" + phone.substring(3, 6) + "-" + phone.substring(6)

        smsManager.sendTextMessage(phone, null, message, null, null)
        Toast.makeText(context, "$phoneNum: $message", Toast.LENGTH_SHORT).show()
        Log.i("MainActivity", "repeat")
    }
}
