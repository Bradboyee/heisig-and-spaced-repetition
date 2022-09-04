package com.example.finalproject.json

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AllKanjiJson(context: Context) {
    private val jsonString = context.assets.open("AllKanji.json").bufferedReader().use {
        it.readText()
    }
    val data = Json.decodeFromString<List<PojoKanjiAllJson>>(jsonString)

    fun getGrade(targetGrade:Int): List<String> {
        val filtered = data.filter { it.grade == targetGrade }
        return filtered.map { it.character }
    }
}
