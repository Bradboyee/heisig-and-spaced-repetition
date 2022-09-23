package com.example.finalproject

import android.graphics.Color.parseColor

object Constant {
    const val BASE_URL_KANJI_ALIVE = "https://kanjialive-api.p.rapidapi.com"
    const val loadingImage = R.drawable.table
    const val errorImage = R.drawable.baseline_circle_24
    const val databaseName = "spaced_database"
    //color
    val sunset_orange = parseColor("#FF605C")
    val pastel_orange = parseColor("#FFBD44")
    val malachite = parseColor("#00CA4E")
    //notification
    //10000 = 1 day = 8.64 second this is for testing
    // if u want to get normal 1 day time set this to 1
    const val notification_test = 10000
}