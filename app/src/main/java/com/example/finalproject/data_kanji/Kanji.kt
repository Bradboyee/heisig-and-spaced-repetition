package com.example.finalproject.data_kanji

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kanji(
    val kanji:String,
    val Japanese_Language_Proficiency_Test:Int,
    var kanjiReadingKun: String,
    var kanjiReadingOn: String,
    var kanjiMeaning: String,
    var component1: String,
    var component1ReadingKun: String,
    var component1ReadingOn: String,
    var component1Meaning: String,
    var component2: String,
    var component2ReadingKun: String,
    var component2ReadingOn: String,
    var component2Meaning: String,
    var story: String,
    var spacedstatus:Int,
    ):Parcelable
