package com.example.finalproject.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.finalproject.notification.alarm.Massage

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.getBundleExtra("bundle")
        val massage = bundle!!.getParcelable<Massage>("massage")
        Log.i("massage", massage.toString())

        val builder =
            NotificationModel(context, massage!!.title, massage.content).createNotificationBuilder()
        val notificationCall = CreateChannel(context, builder)
        notificationCall.createNotificationChannel()
        notificationCall.startNotify(2)
    }
}