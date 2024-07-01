package com.example.sagligimaklimda.viewmodel

import android.Manifest
import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sagligimaklimda.AlarmReceiver
import com.example.sagligimaklimda.model.DateModel
import com.example.sagligimaklimda.serviceDate.DateDatabase
import com.example.sagligimaklimda.serviceDate.DateRepo

import kotlinx.coroutines.launch
import java.util.Calendar

class DateViewModel (application: Application): AndroidViewModel(application) {

    var readAllData: LiveData<List<DateModel>>
    var repostory: DateRepo

    init {
        val dateDao = DateDatabase.getDatabase(application).dateDao()
        repostory = DateRepo(dateDao)
        readAllData = dateDao.getAllDateModels()
    }

    fun impDate(dateModel: DateModel) {
        viewModelScope.launch {
            repostory.addDateModel(dateModel)
            setAlarm(getApplication(), dateModel, dateModel.id, dateModel.Doctor, dateModel.Hospital, dateModel.time)


        }

    }

    fun deleteAll() {
        viewModelScope.launch {
            repostory.deleteAll()
        }
    }



    fun update(dateModel: DateModel) {
        viewModelScope.launch {
            repostory.updateDateModel(dateModel)
            setAlarm(getApplication(), dateModel, dateModel.id, dateModel.Doctor, dateModel.Hospital, dateModel.time)


        }

    }

    fun delete(dateModel: DateModel) {
        viewModelScope.launch {
            repostory.deleteDateModel(dateModel)
        }

    }
    fun setAlarm(context: Context, checkUpDate: DateModel, alarmId: Int, doctor :String, hospital:String, time:Long) {
        createNotificationChannel(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.example.ACTION_SET_ALARM"
            putExtra("REMINDER_TYPE", "CHECK_UP")
            putExtra("REMINDER_TITLE", hospital)
            putExtra("DESC", doctor)
            putExtra("ALARM_ID", alarmId)
        }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = checkUpDate.Date
            add(Calendar.HOUR_OF_DAY, -3)
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.SCHEDULE_EXACT_ALARM)
                == PackageManager.PERMISSION_GRANTED) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
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

