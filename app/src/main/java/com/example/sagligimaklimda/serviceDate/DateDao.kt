package com.example.sagligimaklimda.serviceDate

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sagligimaklimda.model.DateModel

@Dao
interface DateDao {

@Insert
suspend fun addDateModel(dateModel: DateModel)

@Delete
suspend fun deleteDateModel(dateModel: DateModel)

@Update
suspend fun updateDateModel(dateModel: DateModel)

@Query("SELECT * from date_model_table order by id ASC")
fun getAllDateModels(): LiveData<List<DateModel>>

@Query("DELETE FROM date_model_table WHERE id = :dateModelId")
suspend fun deleteDateModelById(dateModelId: Int)

@Query("DELETE FROM date_model_table")
suspend fun deleteAll()
}