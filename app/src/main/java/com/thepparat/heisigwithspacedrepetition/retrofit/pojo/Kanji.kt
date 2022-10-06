package com.thepparat.heisigwithspacedrepetition.retrofit.pojo

data class Kanji(
    val character: String,
    val kunyomi: Kunyomi,
    val meaning: Meaning,
    val onyomi: Onyomi,
    val strokes: Strokes,
    val video: Video
)