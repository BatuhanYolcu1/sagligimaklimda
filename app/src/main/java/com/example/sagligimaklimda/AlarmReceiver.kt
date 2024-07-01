package com.example.sagligimaklimda

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.sagligimaklimda.R
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.ACTION_SET_ALARM") {
            val reminderType = intent.getStringExtra("REMINDER_TYPE")
            val alarmId = intent.getIntExtra("ALARM_ID", 0)
            val endDate = intent.getLongExtra("END_DATE", 0L)
            val reminderTitle = intent.getStringExtra("REMINDER_TITLE")
            val reminderDesc = intent.getStringExtra("DESC")
            if (reminderType == "DRUG") {
                val currentDate = Calendar.getInstance().timeInMillis
                if (currentDate >= endDate) {
                    cancelAlarm(context, alarmId)
                    return
                }
                val notification = createNotification(context, reminderTitle!!, reminderDesc!!)
                showNotification(context, alarmId, notification)
            } else if (reminderType == "CHECK_UP") {
                val checkUpTitle = intent.getStringExtra("REMINDER_TITLE")
                val hospital = intent.getStringExtra("DESC")
                val checkUpNotification = createCheckUpNotification(context, checkUpTitle!!,hospital!!)
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(alarmId, checkUpNotification)
            }
        }
    }

    private fun cancelAlarm(context: Context, alarmId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }



    private fun createNotification(context: Context, reminderTitle: String, reminderDesc: String): Notification {
        val channelId = "YOUR_CHANNEL_ID"
        val contentTitle = "$reminderTitle içme zamanı"
        val contentText = reminderDesc
        val icon = R.drawable.icon // Bildirimde gösterilecek ikon

        return NotificationCompat.Builder(context, channelId).apply {
            setContentTitle(contentTitle)
            setContentText(contentText)
            setSmallIcon(icon)
            setAutoCancel(true)
        }.build()
    }

    private fun createCheckUpNotification(context: Context, checkUpTitle: String, hospital:String): Notification {
        val channelId = "YOUR_CHANNEL_ID"
        createNotification(context, "Check Up Reminder", "3 saat sonra Dr. $checkUpTitle ile $hospital'da randevunuz vardır")

        return NotificationCompat.Builder(context, channelId).apply {
            setContentTitle("Check Up Reminder")
            setContentText("3 saat sonra Dr. $checkUpTitle ile $hospital'da randevunuz vardır")
            setSmallIcon(R.drawable.icon) // Bildirimde gösterilecek ikon
            setAutoCancel(true)
            setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()
    }

    private fun showNotification(context: Context, notificationId: Int, notification: Notification) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}