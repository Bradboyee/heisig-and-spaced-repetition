package com.example.finalproject.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kanji_table")
data class KanjiEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val kanji:String,
    val JLPT:Int
)
