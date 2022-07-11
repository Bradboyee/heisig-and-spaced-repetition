package com.example.finalproject.roomdatabase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "kanji_table")
data class KanjiEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val kanji:String,
    val JLPT:Int,
    var KanjiReadingKun: String,
    var KanjiReadingOn: String,
    var KanjiMeaning: String,
    var Component1: String,
    var Component1ReadingKun: String,
    var Component1ReadingOn: String,
    var Component1Meaning: String,
    var Component2: String,
    var Component2ReadingKun: String,
    var Component2ReadingOn: String,
    var Component2Meaning: String,
    var Story: String
):Parcelable
