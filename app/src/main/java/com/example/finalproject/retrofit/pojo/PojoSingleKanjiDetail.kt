package com.example.finalproject.retrofit.pojo

data class PojoSingleKanjiDetail(
    val examples: List<Example>,
    val kanji: Kanji,
    val radical: Radical,
    val references: References
)