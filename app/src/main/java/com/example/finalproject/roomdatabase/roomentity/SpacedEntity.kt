package com.example.finalproject.roomdatabase.roomentity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "kanji_table")
data class SpacedEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val kanji: String,
    val Grade: Int,
    var kanjiMeaning: String,
    @Embedded
    var status: Status,
) : Parcelable

@Parcelize
data class Status(
    var spacedStatus: Int,
    val addDate: Date,
    val spacedDate: Date,
    val correct: Int,
    val wrong: Int,
) : Parcelable
