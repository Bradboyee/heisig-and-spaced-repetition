package com.example.finalproject.data_kanji

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kanji(
    var Kanji: String,
    var JLPT:Int
):Parcelable
