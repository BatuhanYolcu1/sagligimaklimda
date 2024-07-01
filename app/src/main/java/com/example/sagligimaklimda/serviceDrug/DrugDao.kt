package com.example.sagligimaklimda.serviceDrug

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sagligimaklimda.model.Drug

@Dao
interface DrugDao {

    @Insert
    suspend fun addDrug(drug: Drug)

    @Delete
    suspend fun deleteDrug(drug:Drug)

    @Update
    suspend fun updateDrug(drug: Drug)


    @Query("SELECT * from drug_table order by id ASC")
    fun getAllDrugs(): LiveData<List<Drug>>

    @Query("DELETE FROM drug_table WHERE id = :drugId")
    suspend fun deleteDrugById(drugId: Int)

    @Query("DELETE FROM drug_table")
    suspend fun deleteAll()






}