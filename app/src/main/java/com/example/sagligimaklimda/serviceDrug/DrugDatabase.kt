package com.example.sagligimaklimda.serviceDrug

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sagligimaklimda.model.Drug

@Database(entities = [Drug::class],version = 2,exportSchema = false)
abstract class DrugDatabase : RoomDatabase() {

    abstract fun drugDao(): DrugDao

    companion object {
        @Volatile
        private var INSTANCE: DrugDatabase? = null

        fun getDatabase(context: Context): DrugDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this)
            {
                val instance =
                    Room.databaseBuilder(context, DrugDatabase::class.java, "drugs").build()
                tempInstance = instance
                return instance
            }
        }

    }
}