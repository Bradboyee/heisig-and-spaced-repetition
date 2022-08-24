package com.example.finalproject.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.finalproject.MainActivity
import com.example.finalproject.R

const val  CHANNEL_ID = "SPACE"
class NotificationModel (val context:Context, private val title:String, val content:String){

    private val notifyIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    private val notifyPendingIntent: PendingIntent = PendingIntent.getActivity(
        context, 0, notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    fun createNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_circle)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(notifyPendingIntent)
    }
}