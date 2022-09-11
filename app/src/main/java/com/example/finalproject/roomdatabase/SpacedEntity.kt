package com.example.finalproject.roomdatabase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "kanji_table")
data class SpacedEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val kanji:String,
    val Grade:Int,
    var kanjiMeaning: String,
    var story: String,
    var spacedStatus: Int,
    var addDate: Date,
    var spacedDate:Date
):Parcelable
