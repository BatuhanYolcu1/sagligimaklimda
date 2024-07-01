package com.example.sagligimaklimda.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "drug_table")
@Parcelize
data class Drug(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
                  var name: String,
                  var description:String,
                  val time: Long,
                  val endDate: Long,
                  val timesPerDay:Int
                  ): Parcelable

