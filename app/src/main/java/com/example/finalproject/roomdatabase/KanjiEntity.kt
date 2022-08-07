package com.example.finalproject.roomdatabase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "kanji_table")
data class KanjiEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val kanji:String,
    val Japanese_Language_Proficiency_Test:Int,
    var kanjiReadingKun: String,
    var kanjiReadingOn: String,
    var kanjiMeaning: String,
    var component1kanji: String,
    var component1ReadingKun: String,
    var component1ReadingOn: String,
    var component1Meaning: String,
    var component2kanji: String,
    var component2ReadingKun: String,
    var component2ReadingOn: String,
    var component2Meaning: String,
    var story: String,
    var spacedStatus: Int,
    var addDate: Date,
    var spacedDate:Date
):Parcelable
