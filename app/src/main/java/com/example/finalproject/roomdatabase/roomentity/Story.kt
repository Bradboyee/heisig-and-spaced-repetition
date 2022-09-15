package com.example.finalproject.roomdatabase.roomentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Story(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val kanji: String,
    val story: String
    )