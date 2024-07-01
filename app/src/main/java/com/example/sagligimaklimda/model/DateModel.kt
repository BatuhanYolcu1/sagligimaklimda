package com.example.sagligimaklimda.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "date_model_table")
data class DateModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val Date: Long,
    val Doctor: String,
    val Hospital: String,
    val time: Long,

    ) : Parcelable