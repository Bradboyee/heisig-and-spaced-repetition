package com.example.finalproject.notification.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import com.example.finalproject.notification.MyReceiver

class AlarmManagerCall(var context: Context, var title: String, var content: String) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val oneDay = 86400

    fun startAlarm() {
        val intent = Intent(context, MyReceiver::class.java)
        val bundle = Bundle()
        bundle.putParcelable("massage", Massage(title, content))
        intent.putExtra("bundle", bundle)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE)
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 5000, pendingIntent)
    }
}