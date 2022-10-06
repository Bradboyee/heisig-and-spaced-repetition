package com.thepparat.heisigwithspacedrepetition.notification.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import com.thepparat.heisigwithspacedrepetition.Constant.notification_test
import com.thepparat.heisigwithspacedrepetition.notification.MyReceiver
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.SpacedEntity
import java.util.*

class AlarmManagerCall(var context: Context, val kanji: SpacedEntity, time: Date) {
    private val newDate = time.time
    private val oldDate = kanji.status.spacedDate.time
    private val notificationTime = newDate - oldDate
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val title = "Hey ,You have Review today !"
    private val content = "${kanji.kanji} is waiting you to review .."
    private val pendingID = kanji.id
    fun startAlarm() {
        val intent = Intent(context, MyReceiver::class.java)
        val bundle = Bundle()
        bundle.putParcelable("massage", Massage(title, content,pendingID))
        intent.putExtra("bundle", bundle)
        val pendingIntent = PendingIntent.getBroadcast(context, pendingID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE)
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + notificationTime/notification_test, pendingIntent)
    }
}