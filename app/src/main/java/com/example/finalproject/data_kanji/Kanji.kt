package com.example.finalproject.data_kanji

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kanji(
    var Kanji: String,
    var JLPT:Int,
    var KanjiReadingKun : String,
    var KanjiReadingOn : String,
    var KanjiMeaning : String,
    var Component1 : String,
    var Component1ReadingKun : String,
    var Component1ReadingOn : String,
    var Component1Meaning : String,
    var Component2 : String,
    var Component2ReadingKun : String,
    var Component2ReadingOn : String,
    var Component2Meaning : String,
    var Story : String,

    ):Parcelable
