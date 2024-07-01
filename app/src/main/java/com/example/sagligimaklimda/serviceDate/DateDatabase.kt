package com.example.sagligimaklimda.serviceDate

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sagligimaklimda.model.DateModel


@Database(entities = [DateModel::class],version = 2,exportSchema = false)
abstract class DateDatabase : RoomDatabase() {

    abstract fun dateDao(): DateDao

    companion object {
        @Volatile
        private var INSTANCE: DateDatabase? = null

        fun getDatabase(context: Context): DateDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this)
            {
                val instance =
                    Room.databaseBuilder(context, DateDatabase::class.java, "dates").build()
                tempInstance = instance
                return instance
            }
        }

    }
}