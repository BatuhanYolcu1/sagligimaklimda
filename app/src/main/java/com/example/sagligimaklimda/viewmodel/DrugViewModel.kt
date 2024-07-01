package com.example.sagligimaklimda.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sagligimaklimda.AlarmReceiver
import com.example.sagligimaklimda.model.Drug
import com.example.sagligimaklimda.serviceDrug.DrugDatabase
import com.example.sagligimaklimda.serviceDrug.DrugRepo
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class DrugViewModel(application: Application) : AndroidViewModel(application) {

    var readAllData: LiveData<List<Drug>>
    var repository: DrugRepo

    init {
        val drugDao = DrugDatabase.getDatabase(application).drugDao()
        repository = DrugRepo(drugDao)
        readAllData = drugDao.getAllDrugs()
    }

    fun impDrug(context: Context, drug: Drug) {
        viewModelScope.launch {
            repository.addDrug(drug)
            setAlarm(drug.id, drug.time, drug.endDate, drug.name, drug.timesPerDay, drug.description)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun removeDrug(drug: Drug) {
        viewModelScope.launch {
            repository.deleteDrug(drug)
        }
    }

    fun update(context: Context, drug: Drug) {
        viewModelScope.launch {
            repository.updateDrug(drug)
            setAlarm(drug.id, drug.time, drug.endDate, drug.name, drug.timesPerDay, drug.description)
        }
    }

    fun delete(drug: Drug) {
        viewModelScope.launch {
            repository.deleteDrug(drug)
        }
    }

    private fun setAlarm(
        alarmId: Int,
        time: Long,
        endDate: Long,
        reminderTitle: String,
        timesPerDay: Int,
        reminderDesc: String
    ) {
        val context = getApplication<Application>().applicationContext
        createNotificationChannel(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.example.ACTION_SET_ALARM"
            putExtra("ALARM_ID", alarmId)
            putExtra("END_DATE", endDate)
            putExtra("REMINDER_TITLE", reminderTitle)
            putExtra("TIMES_PER_DAY", timesPerDay)
            putExtra("DESC", reminderDesc)
            putExtra("REMINDER_TYPE", "DRUG")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        val interval: Long = if (timesPerDay == 2) {
            TimeUnit.HOURS.toMillis(12) // 12 saatte bir
        } else {
            TimeUnit.HOURS.toMillis(5) // 5 saatte bir
        }
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "YOUR_CHANNEL_ID"
            val channelName = "YOUR_CHANNEL_NAME"
            val channelDescription = "YOUR_CHANNEL_DESCRIPTION"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
