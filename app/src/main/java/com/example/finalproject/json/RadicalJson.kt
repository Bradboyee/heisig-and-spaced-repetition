package com.example.finalproject.json

import android.content.Context
import com.example.finalproject.json.jsonpojo.PojoRadicalItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RadicalJson(context: Context) {
    private val jsonString = context.assets.open("radical.json").bufferedReader().use {
        it.readText()
    }
    val data = Json.decodeFromString<List<PojoRadicalItem>>(jsonString)
    fun getRadicalDetail(radical: String): PojoRadicalItem? {
        return data.find { it.radical == radical }
    }
}