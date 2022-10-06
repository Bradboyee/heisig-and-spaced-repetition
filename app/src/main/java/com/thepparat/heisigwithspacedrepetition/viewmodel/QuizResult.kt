package com.thepparat.heisigwithspacedrepetition.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizResult(
    val kanji: String,
    val answer: String,
    val submit: String):Parcelable
