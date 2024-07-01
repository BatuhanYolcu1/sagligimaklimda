package com.example.sagligimaklimda.serviceDate

import androidx.lifecycle.LiveData
import com.example.sagligimaklimda.model.DateModel

class DateRepo(private val dateDao: DateDao) {

    val readAllData: LiveData<List<DateModel>> = dateDao.getAllDateModels()

    suspend fun addDateModel(dateModel: DateModel) {
        dateDao.addDateModel(dateModel)
    }

    suspend fun deleteDateModel(dateModel: DateModel) {
        dateDao.deleteDateModel(dateModel)

    }

    suspend fun updateDateModel(dateModel: DateModel) {
        dateDao.updateDateModel(dateModel)

    }

    suspend fun deleteAll() {
        dateDao.deleteAll()
    }

}
